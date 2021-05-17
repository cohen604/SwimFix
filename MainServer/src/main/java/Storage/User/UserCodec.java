package Storage.User;

import Domain.UserData.*;
import Storage.Admin.AdminDao;
import Storage.Swimmer.SwimmerDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UserCodec implements Codec<User> {

    private final CodecRegistry _codecRegistry;
    private SwimmerDao _swimmerDao;
    private AdminDao _adminDao;

    public UserCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
        _swimmerDao = new SwimmerDao();
        _adminDao = new AdminDao();
    }

    @Override
    public User decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String id = bsonReader.readString("_id");
        String email = bsonReader.readString("email");
        String name = bsonReader.readString("name");
        boolean logged = bsonReader.readBoolean("logged");

        Swimmer swimmer = null;
        String objectName = null;
        Coach coach = null;
        Admin admin = null;
        Researcher researcher = null;

        objectName = bsonReader.readName();

        if(objectName.equals("swimmer")) {
            String swimmerId = bsonReader.readString();
            swimmer = _swimmerDao.find(swimmerId);
        }

        if(objectName.equals("coach")) {
            Codec<Coach> coachCodec = _codecRegistry.get(Coach.class);
            coach = decoderContext.decodeWithChildContext(coachCodec, bsonReader);
            if(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                objectName = bsonReader.readName();
            }
        }

        if(objectName.equals("admin")) {
            String adminId = bsonReader.readString();
            admin =  _adminDao.find(adminId);
        }

        if(objectName.equals("researcher")) {
            Codec<Researcher> researcherCodec = _codecRegistry.get(Researcher.class);
            researcher = decoderContext.decodeWithChildContext(researcherCodec,bsonReader);
        }

        bsonReader.readEndDocument();
        return new User(id, email, name, logged, swimmer, coach, admin, researcher);
    }

    @Override
    public void encode(BsonWriter bsonWriter, User user, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", user.getUid());
        bsonWriter.writeString("email", user.getEmail());
        bsonWriter.writeString("name", user.getName());
        bsonWriter.writeBoolean("logged", user.isLogged());

        if(user.getSwimmer() != null) {
            bsonWriter.writeString("swimmer", user.getSwimmer().getEmail());
            _swimmerDao.tryInsertThenUpdate(user.getSwimmer());
        }

        if(user.getCoach() != null) {
            Codec dateCodec = _codecRegistry.get(Coach.class);
            bsonWriter.writeName("coach");
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getCoach());
        }

        if(user.getAdmin() != null) {
            bsonWriter.writeString("admin", user.getAdmin().getEmail());
            _adminDao.tryInsertThenUpdate(user.getAdmin());
        }

        if(user.getResearcher() != null) {
            Codec dateCodec = _codecRegistry.get(Researcher.class);
            bsonWriter.writeName("researcher");
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getResearcher());
        }

        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}

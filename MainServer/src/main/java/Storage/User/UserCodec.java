package Storage.User;

import Domain.UserData.*;
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

    public UserCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public User decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String id = bsonReader.readString("_id");
        String email = bsonReader.readString("email");
        String name = bsonReader.readString("name");
        boolean logged = bsonReader.readBoolean("logged");

        Codec<Swimmer> swimmerCodec = _codecRegistry.get(Swimmer.class);
        bsonReader.readName("swimmer");
        Swimmer swimmer = swimmerCodec.decode(bsonReader, decoderContext);

        Codec<Coach> coachCodec = _codecRegistry.get(Coach.class);
        bsonReader.readName("coach");
        Coach coach = coachCodec.decode(bsonReader, decoderContext);

        Codec<Admin> adminCodec = _codecRegistry.get(Admin.class);
        bsonReader.readName("admin");
        Admin admin = adminCodec.decode(bsonReader, decoderContext);

        Codec<Researcher> researcherCodec = _codecRegistry.get(Researcher.class);
        bsonReader.readName("researcher");
        Researcher researcher = researcherCodec.decode(bsonReader, decoderContext);

        return new User(id, email, name, logged, swimmer, coach, admin, researcher);
    }

    @Override
    public void encode(BsonWriter bsonWriter, User user, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", user.getUid());
        bsonWriter.writeString("email", user.getEmail());
        bsonWriter.writeString("name", user.getName());
        bsonWriter.writeBoolean("logged", user.isLogged());

        Codec dateCodec = _codecRegistry.get(Swimmer.class);
        bsonWriter.writeName("swimmer");
        encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getSwimmer());

        dateCodec = _codecRegistry.get(Coach.class);
        bsonWriter.writeName("coach");
        encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getCoach());

        dateCodec = _codecRegistry.get(Admin.class);
        bsonWriter.writeName("admin");
        encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getAdmin());

        dateCodec = _codecRegistry.get(Researcher.class);
        bsonWriter.writeName("researcher");
        encoderContext.encodeWithChildContext(dateCodec, bsonWriter, user.getResearcher());

        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}

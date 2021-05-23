package Storage.Coach.Codecs;
import Domain.UserData.Coach;
import Domain.UserData.Team;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class CoachCodec implements Codec<Coach> {

    private final CodecRegistry _codecRegistry;

    public CoachCodec() {
        _codecRegistry = null;
    }

    public CoachCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public Coach decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String email = bsonReader.readString("id");
        Team team = null;
        String objectName = bsonReader.readName();
        if(objectName.equals("team")) {
            Codec<Team> teamCodec = _codecRegistry.get(Team.class);
            team = decoderContext.decodeWithChildContext(teamCodec, bsonReader);
            if(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                objectName = bsonReader.readName();
            }
        }
        bsonReader.readEndDocument();
        return new Coach(email, team);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Coach coach, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("id", coach.getEmail());
        if(coach.getTeam()!=null) {
            Codec<Team> teamCodec = _codecRegistry.get(Team.class);
            bsonWriter.writeName("team");
            encoderContext.encodeWithChildContext(teamCodec, bsonWriter, coach.getTeam());
        }
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Coach> getEncoderClass() {
        return Coach.class;
    }
}

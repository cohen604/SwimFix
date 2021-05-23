package Storage.Coach.Codecs;
import Domain.UserData.Coach;
import Domain.UserData.Team;
import Storage.Team.TeamDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class CoachCodec implements Codec<Coach> {

    private TeamDao teamDao;

    public CoachCodec() {
        this.teamDao = new TeamDao();
    }

    @Override
    public Coach decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String email = bsonReader.readString("_id");
        String teamId = bsonReader.readString("team_id");
        Team team = this.teamDao.find(teamId);
        bsonReader.readEndDocument();
        return new Coach(email, team);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Coach coach, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", coach.getEmail());
        bsonWriter.writeString("team_id", coach.getTeam().getName());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Coach> getEncoderClass() {
        return Coach.class;
    }
}

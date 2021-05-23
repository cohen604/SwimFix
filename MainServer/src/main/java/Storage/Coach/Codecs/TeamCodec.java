package Storage.Coach.Codecs;

import Domain.UserData.Team;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class TeamCodec implements Codec<Team> {

    @Override
    public Team decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Team team, EncoderContext encoderContext) {

    }

    @Override
    public Class<Team> getEncoderClass() {
        return Team.class;
    }
}

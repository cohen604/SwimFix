package Storage.User;
import Domain.UserData.Coach;
import org.bson.BsonReader;
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
        String tag = bsonReader.readString("tag");
        bsonReader.readEndDocument();
        return new Coach(tag);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Coach coach, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", coach.getTag());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Coach> getEncoderClass() {
        return Coach.class;
    }
}

package Storage.User;

import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Swimmer;
import Domain.UserData.User;
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

public class SwimmerCodec implements Codec<Swimmer> {

    private final CodecRegistry _codecRegistry;

    public SwimmerCodec() {
        _codecRegistry = null;
    }

    public SwimmerCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public Swimmer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String tag = bsonReader.readString("tag");
        return new Swimmer(tag);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", swimmer.getTag());
        /*bsonWriter.writeStartArray("feedbacks");
        for (IFeedbackVideo feedbackVideo: swimmer.getFeedbacks()) {
            bsonWriter.writeString(feedbackVideo.getPath(), feedbackVideo.getIVideo().getPath());
        }
        bsonWriter.writeEndArray();*/
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}

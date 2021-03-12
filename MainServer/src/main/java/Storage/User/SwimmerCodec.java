package Storage.User;

import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;
import Domain.SwimmingData.SwimmingError;
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
        List<IFeedbackVideo> feedbacks = new LinkedList<>();
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            bsonReader.readStartDocument();
            String feedbackPath = bsonReader.readString("path");
            String videoPath = bsonReader.readString("video path");
            String skeletonsPath = bsonReader.readString("skeletons path");
            String mlSkeletonsPath = bsonReader.readString("ml skeletons path");
            //TODO need to change this new to something else
            //FeedbackVideo feedbackVideo = new FeedbackVideo();
            //feedbacks.add(feedbackVideo);
            bsonReader.readEndDocument();
        }
        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        return new Swimmer();
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();

        bsonWriter.writeStartArray("feedbacks");
        for (IFeedbackVideo feedbackVideo: swimmer.getFeedbacks()) {
            bsonWriter.writeStartDocument();
            bsonWriter.writeString("path", feedbackVideo.getPath());
            bsonWriter.writeString("video path", feedbackVideo.getIVideo().getPath());
            bsonWriter.writeString("skeletons path", feedbackVideo.getSkeletonsPath());
            bsonWriter.writeString("ml skeletons path", feedbackVideo.getMLSkeletonsPath());
            bsonWriter.writeString(feedbackVideo.getPath(), feedbackVideo.getIVideo().getPath());
            bsonWriter.writeName("errors");
            bsonWriter.writeStartDocument();
            bsonWriter.writeEndDocument();
            bsonWriter.writeEndDocument();
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}

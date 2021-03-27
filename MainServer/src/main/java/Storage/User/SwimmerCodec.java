package Storage.User;

import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.SwimmingData.Errors.*;
import Domain.SwimmingData.SwimmingError;
import Domain.UserData.Swimmer;
import Domain.UserData.User;
import DomainLogic.FileLoaders.SkeletonsLoader;
import Storage.SwimmingErrors.RightElbowErrorCodec;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.*;

public class SwimmerCodec implements Codec<Swimmer> {

    private final CodecRegistry _codecRegistry;

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
            String videoPath = bsonReader.readString("video_path");
            String skeletonsPath = bsonReader.readString("skeletons_path");
            String mlSkeletonsPath = bsonReader.readString("ml_skeletons_path");
            Map<Integer, List<SwimmingError>> map = decodeErrorMap(bsonReader, decoderContext);
            bsonReader.readEndDocument();
            feedbacks.add(createFeedback(feedbackPath, videoPath, skeletonsPath, mlSkeletonsPath, map));
        }
        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        return new Swimmer(feedbacks);
    }

    //TODO refactor to factory
    public FeedbackVideo createFeedback(String feedbackPath,
                                        String videoPath,
                                        String skeletonsPath,
                                        String mlSkeletonsPath,
                                        Map<Integer, List<SwimmingError>> map) {
        Video video = new Video(videoPath);
        SkeletonsLoader loader = new SkeletonsLoader();
        TaggedVideo taggedVideo = new TaggedVideo(loader.read(skeletonsPath),mlSkeletonsPath,skeletonsPath);
        return new FeedbackVideo(video, taggedVideo,map, feedbackPath);
    }

    public Map<Integer, List<SwimmingError>> decodeErrorMap(BsonReader bsonReader, DecoderContext decoderContext) {
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        bsonReader.readStartDocument();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            int index = Integer.parseInt(bsonReader.readName());
            List<SwimmingError> errors = new LinkedList<>();
            bsonReader.readStartArray();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                errors.add(decodeSwimmingError(bsonReader, decoderContext));
            }
            bsonReader.readEndArray();
            errorMap.put(index, errors);
        }
        bsonReader.readEndDocument();
        return errorMap;
    }

    public SwimmingError decodeSwimmingError(BsonReader bsonReader, DecoderContext decoderContext) {
        SwimmingError swimmingError = null;
        bsonReader.readStartDocument();
        String tag = bsonReader.readString("tag");
        if(tag.equals("Right Elbow Error")) {
            Codec<RightElbowError> codec = _codecRegistry.get(RightElbowError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Elbow Error")) {
            Codec<LeftElbowError> codec = _codecRegistry.get(LeftElbowError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Right Forearm Error")) {
            Codec<RightForearmError> codec = _codecRegistry.get(RightForearmError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Forearm Error")) {
            Codec<LeftForearmError> codec = _codecRegistry.get(LeftForearmError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Right Palm Error")) {
            Codec<RightPalmCrossHeadError> codec = _codecRegistry.get(RightPalmCrossHeadError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Palm Error")) {
            Codec<LeftPalmCrossHeadError> codec = _codecRegistry.get(LeftPalmCrossHeadError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        bsonReader.readEndDocument();
        return swimmingError;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();

        bsonWriter.writeStartArray("feedbacks");
        for (IFeedbackVideo feedbackVideo: swimmer.getFeedbacks()) {
            bsonWriter.writeStartDocument();
            // write fields
            bsonWriter.writeString("path", feedbackVideo.getPath());
            bsonWriter.writeString("video_path", feedbackVideo.getIVideo().getPath());
            bsonWriter.writeString("skeletons_path", feedbackVideo.getSkeletonsPath());
            bsonWriter.writeString("ml_skeletons_path", feedbackVideo.getMLSkeletonsPath());
            bsonWriter.writeName("errors");
            // write swimming errors
            encodeErrorMap(bsonWriter, feedbackVideo.getSwimmingErrors(), encoderContext);
            bsonWriter.writeEndDocument();
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    private void encodeErrorMap(BsonWriter bsonWriter, Map<Integer,List<SwimmingError>> map, EncoderContext context) {
        bsonWriter.writeStartDocument();
        for (Integer key: map.keySet()) {
            List<SwimmingError> errors = map.get(key);
            bsonWriter.writeName(key.toString());
            bsonWriter.writeStartArray();
            for (SwimmingError error : errors) {
                encodeError(bsonWriter, error, context);
            }
            bsonWriter.writeEndArray();
        }
        bsonWriter.writeEndDocument();
    }

    private void encodeError(BsonWriter bsonWriter, SwimmingError error, EncoderContext context) {
        if(error.getTag().equals("Right Elbow Error")) {
            Codec<RightElbowError> dateCodec = _codecRegistry.get(RightElbowError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (RightElbowError)error);
        }
        else if(error.getTag().equals("Left Elbow Error")) {
            Codec<LeftElbowError> dateCodec = _codecRegistry.get(LeftElbowError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (LeftElbowError)error);
        }
        else if(error.getTag().equals("Right Forearm Error")) {
            Codec<RightForearmError> dateCodec = _codecRegistry.get(RightForearmError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (RightForearmError)error);
        }
        else if(error.getTag().equals("Left Forearm Error")) {
            Codec<LeftForearmError> dateCodec = _codecRegistry.get(LeftForearmError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (LeftForearmError)error);
        }
        else if(error.getTag().equals("Right Palm Error")) {
            Codec<RightPalmCrossHeadError> dateCodec = _codecRegistry.get(RightPalmCrossHeadError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (RightPalmCrossHeadError)error);
        }
        else if(error.getTag().equals("Left Palm Error")) {
            Codec<LeftPalmCrossHeadError> dateCodec = _codecRegistry.get(LeftPalmCrossHeadError.class);
            context.encodeWithChildContext(dateCodec, bsonWriter, (LeftPalmCrossHeadError)error);
        }
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}
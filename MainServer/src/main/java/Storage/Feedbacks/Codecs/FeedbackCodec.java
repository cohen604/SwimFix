package Storage.Feedbacks.Codecs;

import Domain.Errors.*;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.PeriodTimeData.SwimmingPeriodTime;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.TextualComment;
import Domain.Streaming.Video;
import DomainLogic.FileLoaders.SkeletonsLoader;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FeedbackCodec implements Codec<FeedbackVideo> {

    private final CodecRegistry _codecRegistry;

    public FeedbackCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public FeedbackVideo decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String feedbackPath = bsonReader.readString("_id");
        String videoPath = bsonReader.readString("video_path");
        String skeletonsPath = bsonReader.readString("skeletons_path");
        String mlSkeletonsPath = bsonReader.readString("ml_skeletons_path");
        String errors = bsonReader.readName();
        Map<Integer, List<SwimmingError>> errorMap = decodeErrorMap(bsonReader, decoderContext);
        String periods = bsonReader.readName();
        Codec<SwimmingPeriodTime> codec = _codecRegistry.get(SwimmingPeriodTime.class);
        SwimmingPeriodTime swimmingPeriodTime = decoderContext.decodeWithChildContext(codec, bsonReader);
        String comments = bsonReader.readName();
        List<TextualComment> commentList = decodeListComments(bsonReader, decoderContext);
        bsonReader.readEndDocument();
        return createFeedback(
                feedbackPath,
                videoPath,
                skeletonsPath,
                mlSkeletonsPath,
                errorMap,
                swimmingPeriodTime,
                commentList);
    }

    private Map<Integer, List<SwimmingError>> decodeErrorMap(BsonReader bsonReader, DecoderContext decoderContext) {
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        bsonReader.readStartDocument();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            int index = Integer.parseInt(bsonReader.readName());
            List<SwimmingError> errors = new LinkedList<>();
            bsonReader.readStartArray();
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                Codec<SwimmingError> codec = _codecRegistry.get(SwimmingError.class);
                SwimmingError swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
                errors.add(swimmingError);
            }
            bsonReader.readEndArray();
            errorMap.put(index, errors);
        }
        bsonReader.readEndDocument();
        return errorMap;
    }

    private List<TextualComment> decodeListComments(BsonReader bsonReader, DecoderContext decoderContext) {
        List<TextualComment> output = new LinkedList<>();
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            Codec<TextualComment> codec = _codecRegistry.get(TextualComment.class);
            TextualComment comment = decoderContext.decodeWithChildContext(codec, bsonReader);
            output.add(comment);
        }
        bsonReader.readEndArray();
        return output;
    }

    private FeedbackVideo createFeedback(
            String feedbackPath,
            String videoPath,
            String skeletonsPath,
            String mlSkeletonsPath,
            Map<Integer, List<SwimmingError>> map,
            SwimmingPeriodTime periodTime,
            List<TextualComment> comments) {
        Video video = new Video(videoPath);
        SkeletonsLoader loader = new SkeletonsLoader();
        TaggedVideo taggedVideo = new TaggedVideo(loader.read(skeletonsPath),mlSkeletonsPath,skeletonsPath);
        return new FeedbackVideo(video, taggedVideo,map, feedbackPath, periodTime, comments);
    }

    @Override
    public void encode(BsonWriter bsonWriter, FeedbackVideo feedbackVideo, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        // write fields
        bsonWriter.writeString("_id", feedbackVideo.getPath());
        bsonWriter.writeString("video_path", feedbackVideo.getIVideo().getPath());
        bsonWriter.writeString("skeletons_path", feedbackVideo.getSkeletonsPath());
        bsonWriter.writeString("ml_skeletons_path", feedbackVideo.getMLSkeletonsPath());
        // write swimming errors
        bsonWriter.writeName("errors");
        encodeErrorMap(bsonWriter, feedbackVideo.getSwimmingErrors(), encoderContext);
        // write time period
        bsonWriter.writeName("periods");
        Codec<SwimmingPeriodTime> dateCodec = _codecRegistry.get(SwimmingPeriodTime.class);
        encoderContext.encodeWithChildContext(dateCodec, bsonWriter,
                (SwimmingPeriodTime) feedbackVideo.getSwimmingPeriodTime());
        // write comments
        bsonWriter.writeName("comments");
        encodeCommentsList(bsonWriter,feedbackVideo.getTextualComments(),encoderContext);
        bsonWriter.writeEndDocument();
    }

    private void encodeErrorMap(BsonWriter bsonWriter, Map<Integer,List<SwimmingError>> map, EncoderContext context) {
        bsonWriter.writeStartDocument();
        for (Integer key: map.keySet()) {
            List<SwimmingError> errors = map.get(key);
            bsonWriter.writeName(key.toString());
            bsonWriter.writeStartArray();
            for (SwimmingError error : errors) {
                Codec<SwimmingError> dateCodec = _codecRegistry.get(SwimmingError.class);
                context.encodeWithChildContext(dateCodec, bsonWriter, error);
            }
            bsonWriter.writeEndArray();
        }
        bsonWriter.writeEndDocument();
    }

    private void encodeCommentsList(BsonWriter bsonWriter, List<TextualComment> comments, EncoderContext encoderContext){
        bsonWriter.writeStartArray();
        for(TextualComment comment: comments) {
            Codec<TextualComment> codec = _codecRegistry.get(TextualComment.class);
            encoderContext.encodeWithChildContext(codec, bsonWriter, comment);
        }
        bsonWriter.writeEndArray();
    }

    @Override
    public Class<FeedbackVideo> getEncoderClass() {
        return FeedbackVideo.class;
    }


}

package Storage.User;

import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Swimmer;
import Storage.Feedbacks.FeedbacksDao;
import Storage.Feedbacks.IFeedbackDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import java.util.*;

public class SwimmerCodec implements Codec<Swimmer> {

    private IFeedbackDao _feedbackDao;

    public SwimmerCodec() {
        _feedbackDao = new FeedbacksDao();
    }

    @Override
    public Swimmer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        List<IFeedbackVideo> feedbacks = new LinkedList<>();
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String id = bsonReader.readString();
            feedbacks.add(_feedbackDao.find(id));
        }
        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        return new Swimmer(feedbacks);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();

        bsonWriter.writeStartArray("feedbacks");
        for (IFeedbackVideo feedbackVideo: swimmer.getFeedbacks()) {
            bsonWriter.writeString(feedbackVideo.getPath());
            //TODO talk about this
            _feedbackDao.tryInsertThenUpdate((FeedbackVideo) feedbackVideo);
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}

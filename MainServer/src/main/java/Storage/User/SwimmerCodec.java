package Storage.User;

import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.PeriodTimeData.SwimmingPeriodTime;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.Errors.*;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.UserData.Swimmer;
import DomainLogic.FileLoaders.SkeletonsLoader;
import Storage.Feedbacks.FeedbacksDao;
import Storage.Feedbacks.IFeedbackDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.transaction.TransactionException;

import javax.jms.TransactionInProgressException;
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
            //TODO talk about this
            _feedbackDao.insert((FeedbackVideo) feedbackVideo);
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}

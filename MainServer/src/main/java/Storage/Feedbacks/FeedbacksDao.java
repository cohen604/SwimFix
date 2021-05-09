package Storage.Feedbacks;

import Domain.Streaming.FeedbackVideo;
import Storage.Dao;
import Storage.DbContext;
import Storage.Feedbacks.Codecs.FeedbackCodec;
import Storage.Feedbacks.Codecs.SwimmingErrors.*;
import Storage.Feedbacks.Codecs.SwimmingPeriodTimeCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class FeedbacksDao extends Dao<FeedbackVideo> implements IFeedbackDao {

    @Override
    public MongoCollection<FeedbackVideo> getCollection() {
        CodecRegistry errorsCodecRegistry = CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(
                new LeftElbowErrorCodec(),
                new LeftForearmErrorCodec(),
                new LeftPalmCrossHeadErrorCodec(),
                new RightElbowErrorCodec(),
                new RightForearmErrorCodec(),
                new RightPalmCrossHeadErrorCodec()
        ));

        CodecRegistry feedbackCodecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new SwimmingErrorCodec(errorsCodecRegistry),
                                new SwimmingPeriodTimeCodec()
                        ), //here we define the codec
                        getDefaultCodecRegistry());


        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new FeedbackCodec(feedbackCodecRegistry)
                        ), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_FEEDBACKS, FeedbackVideo.class);
    }

    @Override
    public FeedbackVideo find(String id) {
        //TODO
        return null;
    }

    @Override
    public FeedbackVideo update(FeedbackVideo value) {
        //TODO
        return null;
    }

    @Override
    public boolean removeFeedback(String id) {
        //TODO
        return false;
    }
}

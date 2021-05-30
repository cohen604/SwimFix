package Storage.Feedbacks;
import Domain.Streaming.FeedbackVideo;
import Storage.Dao;
import Storage.DbContext;
import Storage.Feedbacks.Codecs.FeedbackCodec;
import Storage.Feedbacks.Codecs.SwimmingErrors.*;
import Storage.Feedbacks.Codecs.SwimmingPeriodTimeCodec;
import Storage.Feedbacks.Codecs.TextualCommentCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;


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
                                new SwimmingPeriodTimeCodec(),
                                new TextualCommentCodec()
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
    public FeedbackVideo update(FeedbackVideo value) {
        try {
            MongoCollection<FeedbackVideo> collection = getCollection();
            Document query = new Document("_id", value.getPath());
            UpdateResult result = collection.replaceOne(query, value);
            if (result == null) {
                return null;
            }
            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FeedbackVideo tryInsertThenUpdate(FeedbackVideo value) {
        try {
            MongoCollection<FeedbackVideo> collection = getCollection();
            Document query = new Document("_id", value.getPath());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            UpdateResult result = collection.replaceOne(query, value, options);
            if (result == null) {
                return null;
            }
            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeFeedback(String id) {
        //TODO
        return false;
    }

    @Override
    public Long countFeedbacks() {
        try {
            MongoCollection<FeedbackVideo> collection = getCollection();
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

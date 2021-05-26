package Storage.Coach;

import Domain.UserData.Coach;
import Storage.Coach.Codecs.CoachCodec;
import Storage.Dao;
import Storage.DbContext;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class CoachDao extends Dao<Coach> implements ICoachDao {

    @Override
    protected MongoCollection<Coach> getCollection() {

        CodecRegistry codecRegistryCoach =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new CoachCodec()), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistryCoach).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_COACHES, Coach.class);
    }

    @Override
    public Coach update(Coach value) {
        return defaultUpdate(value, value.getEmail());
    }

    @Override
    public Coach tryInsertThenUpdate(Coach coach) {
        return defaultTryInsertThenUpdate(coach, coach.getEmail());
    }
}

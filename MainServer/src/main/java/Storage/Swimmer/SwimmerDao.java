package Storage.Swimmer;

import Domain.UserData.Swimmer;
import Domain.UserData.User;
import Storage.Dao;
import Storage.DbContext;
import Storage.Swimmer.Codecs.SwimmerCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class SwimmerDao extends Dao<Swimmer> implements ISwimmerDao {

    @Override
    protected MongoCollection<Swimmer> getCollection() {
        CodecRegistry codecRegistryUser =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new SwimmerCodec()), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistryUser).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_SWIMMERS, Swimmer.class);
    }

    @Override
    public Swimmer update(Swimmer value) {
        return defaultUpdate(value, value.getEmail());
    }

    @Override
    public Swimmer tryInsertThenUpdate(Swimmer swimmer) {
        return defualtTryInsertThenUpdate(swimmer, swimmer.getEmail());
    }
}

package Storage.User;
import Domain.UserData.User;
import Storage.Dao;
import Storage.DbContext;
import Storage.Feedbacks.Codecs.SwimmingErrors.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class UserDao extends Dao<User> implements IUserDao{

    @Override
    protected MongoCollection<User> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new CoachCodec(),
                                new AdminCodec(),
                                new ResearcherCodec()
                               ), //here we define the codec
                        //CodecRegistries.fromProviders( new UserCodecProvider()),
                        getDefaultCodecRegistry());

        CodecRegistry codecRegistryUser =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new UserCodec(codecRegistry)), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistryUser).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_USERS, User.class);
    }

    @Override
    public User update(User value) {
        try {
            MongoCollection<User> collection = getCollection();
            Document query = new Document("_id", value.getUid());
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
    public boolean removeUser(String id) {
        try {
            MongoCollection<User> collection = getCollection();
            Document query = new Document("_id", id);
            DeleteResult result = collection.deleteOne(query);
            return result.wasAcknowledged();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

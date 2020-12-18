package Storage.User;
import Domain.Swimmer;
import Domain.User;
import Storage.Dao;
import Storage.Swimmer.SwimmerCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class UserDao implements Dao<User> {

    @Override
    public MongoCollection<User> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new UserCodec()), //here we define the codec
                        getDefaultCodecRegistry());
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();
        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("user", User.class);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User insert(User value) {
        try {
            MongoCollection<User> collection = getCollection();
            collection.insertOne(value);
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User find(String id) {
        try {
            MongoCollection<User> collection = getCollection();
            List<User> users = new LinkedList<>();
            Document query = new Document("_id", id);
            collection.find(query).into(users);
            if(users.isEmpty()) {
                return null;
            }
            return users.get(0);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User value) {
        MongoCollection<User> collection = getCollection();
        Document query = new Document("_id", value.getUid());
        UpdateResult result = collection.replaceOne(query, value);
        if(result == null) {
            return null;
        }
        return value;
    }
}

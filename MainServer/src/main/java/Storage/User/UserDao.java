package Storage.User;
import Domain.UserData.User;
import Storage.Dao;
import Storage.DbContext;
import Storage.User.Codecs.CoachCodec;
import Storage.User.Codecs.UserCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class UserDao extends Dao<User> implements IUserDao{

    @Override
    protected MongoCollection<User> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new CoachCodec()
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

    @Override
    public Collection<User> findUsersThatNotAdmin() {
        try {
            MongoCollection<User> collection = getCollection();
            List<User> output = new LinkedList<>();
            Document query = new Document("admin", null);
            collection.find(query).into(output);
            System.out.println(output.size());
            return output;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<User> findUsersThatNotResearcher() {
        try {
            MongoCollection<User> collection = getCollection();
            List<User> output = new LinkedList<>();
            Document query = new Document("researcher", null);
            collection.find(query).into(output);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countUsers() {
        try {
            MongoCollection<User> collection = getCollection();
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countLoggedUsers() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.eq("logged", true);
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countSwimmers() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.exists("swimmer"),
                    Filters.ne("swimmer", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countLoggedSwimmers() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.eq("logged", true),
                    Filters.exists("swimmer"),
                    Filters.ne("swimmer", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countAdmins() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.exists("admin"),
                    Filters.ne("admin", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countLoggedAdmins() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.eq("logged", true),
                    Filters.exists("admin"),
                    Filters.ne("admin", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countResearchers() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.exists("researcher"),
                    Filters.ne("researcher", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countLoggedResearchers() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.eq("logged", true),
                    Filters.exists("researcher"),
                    Filters.ne("researcher", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countCoaches() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.exists("coach"),
                    Filters.ne("coach", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countLoggedCoaches() {
        try {
            MongoCollection<User> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.eq("logged", true),
                    Filters.exists("coach"),
                    Filters.ne("coach", null));
            return collection.countDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package Storage.Admin;

import Domain.UserData.Admin;
import Storage.Admin.Codecs.AdminCodec;
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

public class AdminDao extends Dao<Admin> implements IAdminDao {

    @Override
    protected MongoCollection<Admin> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new AdminCodec()
                        ), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_ADMINS, Admin.class);
    }

    @Override
    public Admin update(Admin value) {
        return defaultUpdate(value, value.getEmail());
    }

    @Override
    public Admin tryInsertThenUpdate(Admin admin) {
        return defaultTryInsertThenUpdate(admin, admin.getEmail());
    }
}

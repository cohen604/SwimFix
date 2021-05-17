package Storage.Researcher;

import Domain.UserData.Researcher;
import Storage.Dao;
import Storage.DbContext;
import Storage.Researcher.Codecs.ResearcherCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class ResearcherDao extends Dao<Researcher> implements IResearcherDao {

    @Override
    protected MongoCollection<Researcher> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(
                                new ResearcherCodec()
                        ), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_RESEARCHERS, Researcher.class);
    }

    @Override
    public Researcher update(Researcher value) {
        return defaultUpdate(value, value.getEmail());
    }

    @Override
    public Researcher tryInsertThenUpdate(Researcher researcher) {
        return defaultTryInsertThenUpdate(researcher, researcher.getEmail());
    }
}

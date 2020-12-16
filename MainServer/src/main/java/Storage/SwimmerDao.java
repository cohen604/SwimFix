package Storage;

import Domain.Streaming.Video;
import Domain.Swimmer;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.List;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class SwimmerDao implements Dao<Swimmer> {

    @Override
    public MongoCollection<Swimmer> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new SwimmerCodec()), //here we define the codec
                        getDefaultCodecRegistry());
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();
        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("swimmer", Swimmer.class);
    }

    @Override
    public List<Swimmer> getAll() {
        return null;
    }

    @Override
    public Swimmer insert(Swimmer value) {
        try {
            MongoCollection<Swimmer> collection = getCollection();
            collection.insertOne(value);
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

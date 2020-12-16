package Storage;

import Domain.Streaming.Video;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class VideoDao implements Dao<Video> {

    @Override
    public MongoCollection<Video> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new VideoCodec()), //here we define the codec
                        getDefaultCodecRegistry());
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();
        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("video", Video.class);
    }

    @Override
    public List<Video> getAll() {
        try {
            MongoCollection<Video> collection = getCollection();
            List<Video> all = collection.find().into(new ArrayList<>());
            return all;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video insert(Video value) {
        try {
            MongoCollection<Video> collection = getCollection();
            collection.insertOne(value);
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package Storage;

import Domain.Streaming.Video;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

public class VideoService implements Dao<Video> {

    MongoCollection<Video> getVideoCollectionAsDocuments() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("video", Video.class);
    }

    MongoCollection<Video> getVideoCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new VideoCodec()), //here we define the codec
                        MongoClient.getDefaultCodecRegistry());
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(codecRegistry).build();
        // here we define the connection
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost",27017), options);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("video", Video.class);
    }

    @Override
    public List<Video> getAll() {
        try {
            MongoCollection<Video> collection = getVideoCollection();
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
            MongoCollection<Video> collection = getVideoCollection();
            collection.insertOne(value);
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

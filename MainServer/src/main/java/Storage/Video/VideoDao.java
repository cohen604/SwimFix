package Storage.Video;

import Domain.Streaming.IFactoryVideo;
import Domain.Streaming.IVideo;
import Domain.Streaming.Video;
import Storage.Dao;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class VideoDao {//implements Dao<Video> {

    private IFactoryVideo iFactoryVideo;

    public VideoDao(IFactoryVideo iFactoryVideo) {
        this.iFactoryVideo = iFactoryVideo;
    }

    //@Override
    public MongoCollection<Video> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new VideoCodec(iFactoryVideo)), //here we define the codec
                        getDefaultCodecRegistry());
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();
        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("swimfix");
        return mongoDatabase.getCollection("video", Video.class);
    }

    //@Override
    public List<? extends IVideo> getAll() {
        try {
            MongoCollection<Video> collection = getCollection();
            List<Video> all = collection.find().into(new ArrayList<>());
            return all;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Override
    public IVideo insert(IVideo value) {
        System.out.println("a");
        try {
            MongoCollection<Video> collection = getCollection();
            collection.insertOne(new Video(value)); //value
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Override
    public IVideo find(String id) {
        return null;
    }

    //@Override
    public IVideo update(IVideo value) {
        return null;
    }

}

package Storage;

import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.Video;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public abstract class Dao<T> {

    protected abstract MongoCollection<T> getCollection();

    //TODO maybe need here to add cache for faster usage

    public List<T> getAll() {
        try {
            MongoCollection<T> collection = getCollection();
            List<T> all = new LinkedList<>();
            collection.find().into(all);
            return all;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public T insert(T value) {
        try {
            MongoCollection<T> collection = getCollection();
            collection.insertOne(value);
            return value;
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public T find(String id) {
        try {
            MongoCollection<T> collection = getCollection();
            List<T> output = new LinkedList<>();
            Document query = new Document("_id", id);
            collection.find(query).into(output);
            if(output.isEmpty()) {
                return null;
            }
            return output.get(0);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract T update(T value);

    protected T defaultUpdate(T value, String id) {
        try {
            MongoCollection<T> collection = getCollection();
            Document query = new Document("_id", id);
            UpdateResult result = collection.replaceOne(query, value);
            if(result!=null) {
                return value;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected T defaultTryInsertThenUpdate(T value, String id) {
        try {
            MongoCollection<T> collection = getCollection();
            Document query = new Document("_id", id);
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            UpdateResult result = collection.replaceOne(query, value, options);
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
}

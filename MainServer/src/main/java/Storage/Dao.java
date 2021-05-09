package Storage;

import Domain.Streaming.Video;
import com.mongodb.client.MongoCollection;

import java.util.LinkedList;
import java.util.List;

public abstract class Dao<T> {

    protected abstract MongoCollection<T> getCollection();

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

    public abstract T update(T value);

}

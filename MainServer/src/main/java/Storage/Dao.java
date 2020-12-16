package Storage;

import Domain.Streaming.Video;
import com.mongodb.client.MongoCollection;

import java.util.List;

public interface Dao<T> {

    MongoCollection<T> getCollection();
    List<T> getAll();
    T insert(T value);

}

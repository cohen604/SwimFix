package Storage;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();
    T insert(T value);

}

package Storage;

public interface Dao<T> {

    boolean insert(T value);
}

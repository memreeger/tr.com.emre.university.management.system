package abst;

import java.util.Optional;

public interface Writeable<T, K> {
    void add(T obj);

    void update(T obj, K id);

    void delete(K id);

    void deleteAll();
}

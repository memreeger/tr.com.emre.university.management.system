package abst;

import java.util.List;

public interface Readable<T, K> {
    //
    T getById(K id);

    List<T> getAll();

    boolean existsById(K id);

    long count();

    List<T> findAdults();
}

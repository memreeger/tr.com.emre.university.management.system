package abst;

import java.util.List;

public interface LessonReadable<T,K> {
    T getById(K id);

    List<T> getAll();

    boolean existById(K id);

    long count();

}

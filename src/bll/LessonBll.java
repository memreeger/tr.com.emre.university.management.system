package bll;

import abst.LessonReadable;
import abst.Writeable;
import dao.LessonDao;
import model.Lesson;

import java.util.List;

public class LessonBll implements LessonReadable<Lesson, Short>,
        Writeable<Lesson, Short> {

    private static LessonBll instance;

    private LessonBll() {
    }

    public static LessonBll getInstance() {
        if (instance == null) {
            instance = new LessonBll();
        }
        return instance;
    }

    private final LessonDao dao = LessonDao.getInstance();

    @Override
    public Lesson getById(Short id) {
        return dao.getById(id);
    }

    @Override
    public List<Lesson> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean existById(Short id) {
        return dao.existById(id);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public void add(Lesson obj) {
        dao.add(obj);
    }

    @Override
    public void update(Lesson obj, Short id) {
        dao.update(obj, id);

    }

    @Override
    public void delete(Short id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}

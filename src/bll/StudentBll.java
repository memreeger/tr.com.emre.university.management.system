package bll;

import abst.Readable;
import abst.Writeable;
import dao.StudentDao;
import model.Student;

import java.util.List;

public class StudentBll implements Readable<Student, Integer>, Writeable<Student, Integer> {

    private static StudentBll instance;

    private StudentBll() {
    }

    public static StudentBll getInstance() {
        if (instance == null) {
            instance = new StudentBll();
        }
        return instance;
    }

    //private Readable<Student, Integer> dao = StudentDao.getInstance();
    private final StudentDao dao = StudentDao.getInstance();

    //READ

    @Override
    public Student getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public List<Student> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean existsById(Integer id) {
        return dao.existsById(id);
    }

    public List<Student> findAdults() {
        return dao.findAdults();
    }

    @Override
    public long count() {
        return dao.count();
    }

    //WRITE

    @Override
    public void add(Student obj) {
        dao.add(obj);

    }

    @Override
    public void update(Student obj, Integer id) {
        dao.update(obj, id);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}

package bll;

import abst.Readable;
import abst.TeacherRegistrationUpdatable;
import abst.Writeable;
import dao.DaoConnection;
import dao.TeacherDao;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TeacherBll implements Readable<Teacher, Short>,
        Writeable<Teacher, Short>,
        TeacherRegistrationUpdatable<Teacher, Short> {

    private static TeacherBll instance;

    private TeacherBll() {
    }

    public static TeacherBll getInstance() {
        if (instance == null) {
            instance = new TeacherBll();
        }
        return instance;
    }

    private final TeacherDao dao = TeacherDao.getInstance();


    //READ

    @Override
    public Teacher getById(Short id) {
        return dao.getById(id);
    }

    @Override
    public boolean existsById(Short id) {
        return dao.existsById(id);
    }

    @Override
    public List<Teacher> getAll() {
        return dao.getAll();
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public List<Teacher> findAdults() {
        return dao.findAdults();
    }


    //WRITE


    @Override
    public void add(Teacher obj) {
        dao.add(obj);
    }

    @Override
    public void update(Teacher obj, Short id) {
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


    @Override
    public void updateRegistrationNumber(Short number, Short id) {
        dao.updateRegistrationNumber(number, id);
    }
}

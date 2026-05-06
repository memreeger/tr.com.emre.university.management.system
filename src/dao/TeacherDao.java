package dao;

import abst.Readable;
import abst.TeacherRegistrationUpdatable;
import abst.Writeable;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao implements Readable<Teacher, Short>,
        Writeable<Teacher, Short>,
        TeacherRegistrationUpdatable<Teacher, Short> {

    //Singleton
    private static TeacherDao instance;

    private TeacherDao() {
    }

    public static TeacherDao getInstance() {
        if (instance == null) {
            instance = new TeacherDao();
        }
        return instance;
    }


    //READ

    @Override
    public Teacher getById(Short id) {
        String select = "SELECT * FROM teachers WHERE id = ?";

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(select)) {

            statement.setShort(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getShort("id"));
                teacher.setDeleted(rs.getBoolean("isDeleted"));
                teacher.setInsertedDate(rs.getTimestamp("insertedDate"));
                teacher.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
                teacher.setFirstName(rs.getString("firstName"));
                teacher.setLastName(rs.getString("lastName"));
                teacher.setIdentityNumber(rs.getString("identityNumber"));
                teacher.setBirthDate(rs.getDate("birthDate").toLocalDate());
                teacher.setRegistrationNumber(rs.getShort("registrationNumber"));
                return teacher;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teacherList = new ArrayList<>();
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT * FROM teachers";

            PreparedStatement statement = conn.prepareStatement(select);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getShort("id"));
                teacher.setDeleted(rs.getBoolean("isDeleted"));
                teacher.setInsertedDate(rs.getTimestamp("insertedDate"));
                teacher.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
                teacher.setFirstName(rs.getString("firstName"));
                teacher.setLastName(rs.getString("lastName"));
                teacher.setIdentityNumber(rs.getString("identityNumber"));
                teacher.setBirthDate(rs.getDate("birthDate").toLocalDate());
                teacher.setRegistrationNumber(rs.getShort("registrationNumber"));
                teacherList.add(teacher);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacherList;
    }

    @Override
    public boolean existsById(Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT 1 FROM teachers WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(select);
            statement.setShort(1, id);
            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try (Connection conn = DaoConnection.getConnection()) {
            String count = "SELECT COUNT(*) FROM teachers";
            PreparedStatement statement = conn.prepareStatement(count);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> findAdults() {
        List<Teacher> teachers = new ArrayList<>();
        String select = """
                SELECT *
                FROM teachers
                WHERE "birthDate" <= CURRENT_DATE - INTERVAL '18 years'
                """;
        try (Connection conn = DaoConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(select);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getShort("id"));
                teacher.setDeleted(rs.getBoolean("isDeleted"));
                teacher.setInsertedDate(rs.getTimestamp("insertedDate"));
                teacher.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
                teacher.setFirstName(rs.getString("firstName"));
                teacher.setLastName(rs.getString("lastName"));
                teacher.setIdentityNumber(rs.getString("identityNumber"));
                teacher.setBirthDate(rs.getDate("birthDate").toLocalDate());
                teacher.setRegistrationNumber(rs.getShort("registrationNumber"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }


    //WRITE
    @Override
    public void add(Teacher obj) {
        String insert = """
                INSERT INTO teachers (isDeleted, insertedDate, lastUpdateDate, firstName, lastName, identityNumber, birthDate, registrationNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(insert)) {

            statement.setBoolean(1, obj.isDeleted());
            statement.setTimestamp(2, obj.getInsertedDate());
            statement.setTimestamp(3, obj.getLastUpdateDate());
            statement.setString(4, obj.getFirstName());
            statement.setString(5, obj.getLastName());
            statement.setString(6, obj.getIdentityNumber());
            statement.setDate(7, java.sql.Date.valueOf(obj.getBirthDate()));
            statement.setShort(8, obj.getRegistrationNumber());


            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Teacher obj, Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "UPDATE teachers SET registrationNumber = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(select);

            statement.setShort(1, obj.getRegistrationNumber());
            statement.setShort(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String delete = "DELETE FROM teachers WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(delete);
            statement.setShort(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAll() {
        try (Connection conn = DaoConnection.getConnection()) {
            String deleteAll = "DELETE FROM teachers";

            PreparedStatement statement = conn.prepareStatement(deleteAll);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateRegistrationNumber(Short number, Short id) {

        String select = "UPDATE teachers SET registrationNumber = ?  WHERE id = ?";
        try (Connection conn = DaoConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(select);
            statement.setShort(1, number);
            statement.setShort(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

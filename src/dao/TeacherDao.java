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

    //private final LessonTeacherDao lessonTeacherDao = LessonTeacherDao.getInstance();


    //READ

    @Override
    public Teacher getById(Short id) {
        String select = "SELECT * FROM teachers WHERE id = ?";

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(select)) {

            statement.setShort(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapTeacher(rs);
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
                teacherList.add(mapTeacher(rs));
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
                teachers.add(mapTeacher(rs));
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
                INSERT INTO teachers ("isDeleted", "firstName", "lastName", "identityNumber", "birthDate", "registrationNumber")
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        int order = 1;
        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(insert)) {

            statement.setBoolean(order++, obj.isDeleted());
            //statement.setTimestamp(order++, obj.getInsertedDate());
            //statement.setTimestamp(order++, obj.getLastUpdateDate());
            statement.setString(order++, obj.getFirstName());
            statement.setString(order++, obj.getLastName());
            statement.setString(order++, obj.getIdentityNumber());
            statement.setDate(order++, java.sql.Date.valueOf(obj.getBirthDate()));
            statement.setShort(order++, obj.getRegistrationNumber());


            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Teacher obj, Short id) {
        int order = 1;

        try (Connection conn = DaoConnection.getConnection()) {
            String select = """
            UPDATE teachers SET "registrationNumber" = ? WHERE id = ?
            """;
            PreparedStatement statement = conn.prepareStatement(select);

            statement.setShort(order++, obj.getRegistrationNumber());
            statement.setShort(order++, id);

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
        int order = 1;

        String select = """
        UPDATE teachers SET "registrationNumber" = ?  WHERE id = ?
        """;
        try (Connection conn = DaoConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(select);
            statement.setShort(order++, number);
            statement.setShort(order++, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Teacher mapTeacher(ResultSet rs) throws SQLException {
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
        //teacher.setLessons(lessonTeacherDao.getLessonsByTeacherId(teacher.getId()));

        return teacher;
    }
}

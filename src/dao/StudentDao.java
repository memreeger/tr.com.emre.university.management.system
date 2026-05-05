package dao;

import abst.Readable;
import abst.Writeable;
import model.Student;
import model.TakenLesson;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Readable<Student, Integer>, Writeable<Student, Integer> {

    private static StudentDao instance;

    private StudentDao() {
    }

    public static StudentDao getInstance() {
        if (instance == null) {
            instance = new StudentDao();
        }

        return instance;
    }


    //READ


    @Override
    public Student getById(Integer id) {
        Student student;
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT * FROM students WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(select);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();


            if (rs.next()) {
                return mapStudent(rs);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        List<Student> studentList = new ArrayList<>();

        try (Connection conn = DaoConnection.getConnection()) {

            String select = "SELECT * FROM students"; // ? dışarıdan sağlanması gerekn parametre

            PreparedStatement statement = conn.prepareStatement(select); // sql injection

            ResultSet rs = statement.executeQuery();


            while (rs.next()) {
                Student student = mapStudent(rs);
                studentList.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentList;

    }

    @Override
    public boolean existsById(Integer id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(select);

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findAdults() {

        List<Student> students = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM students
                    WHERE "birthDate" <= CURRENT_DATE - INTERVAL '18 years'
                """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Student student = mapStudent(rs);
                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    @Override
    public long count() {
        try (Connection conn = DaoConnection.getConnection()) {
            String count = "SELECT COUNT (*) FROM students";
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


    //WRITE

    @Override
    public void delete(Integer id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String delete = "DELETE FROM students WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(delete);
            statement.setInt(1, id);

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAll() {
        try (Connection conn = DaoConnection.getConnection()) {
            String deleteAll = "DELETE FROM students";

            PreparedStatement statement = conn.prepareStatement(deleteAll);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Student obj) {

        String insert = """
                INSERT INTO students
                (isDeleted, insertedDate, lastUpdateDate, firstName, lastName, identityNumber, birthDate, schoolNumber, grade)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(insert)) {

            statement.setBoolean(1, obj.isDeleted());
            statement.setTimestamp(2, obj.getInsertedDate());
            statement.setTimestamp(3, obj.getLastUpdateDate());
            statement.setString(4, obj.getFirstName());
            statement.setString(5, obj.getLastName());
            statement.setString(6, obj.getIdentityNumber());
            statement.setDate(7, java.sql.Date.valueOf(obj.getBirthDate()));
            statement.setShort(8, obj.getSchoolNumber());
            statement.setDouble(9, obj.getGrade());


            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student obj, Integer id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String update = "UPDATE students SET schoolNumber = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(update);

            statement.setInt(1, obj.getSchoolNumber());
            statement.setInt(2, id);

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //HELPER
    private Student mapStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setDeleted(rs.getBoolean("isDeleted"));
        student.setInsertedDate(rs.getTimestamp("insertedDate"));
        student.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
        student.setFirstName(rs.getString("firstName"));
        student.setLastName(rs.getString("lastName"));
        student.setIdentityNumber(rs.getString("identityNumber"));
        student.setBirthDate(rs.getDate("birthDate").toLocalDate());
        student.setSchoolNumber(rs.getShort("schoolNumber"));
        student.setGrade(rs.getDouble("grade"));
        return student;
    }
}

package dao;

import abst.LessonReadable;
import abst.Writeable;
import enums.LessonType;
import model.Lesson;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao implements LessonReadable<Lesson, Short>,
        Writeable<Lesson, Short> {

    //Singleton
    private static LessonDao instance;

    private LessonDao() {
    }

    public static LessonDao getInstance() {
        if (instance == null) {
            instance = new LessonDao();
        }
        return instance;
    }


    //READABLE

    @Override
    public Lesson getById(Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT * FROM lessons WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(select);
            statement.setShort(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getByte("id"));
                lesson.setDeleted(rs.getBoolean("isDeleted"));
                lesson.setInsertedDate(rs.getTimestamp("insertedDate"));
                lesson.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
                lesson.setName(rs.getString("name"));
                lesson.setCode(rs.getString("code"));
                lesson.setCredit(rs.getByte("credit"));
                lesson.setAvailable(rs.getBoolean("isAvailable"));
                lesson.setLessonType(LessonType.valueOf(rs.getString("lessonType").toUpperCase()));
                return lesson;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Lesson> getAll() {
        List<Lesson> lessonList = new ArrayList<>();
        try (Connection conn = DaoConnection.getConnection()) {
            String selectAll = "SELECT * FROM lessons";
            PreparedStatement statement = conn.prepareStatement(selectAll);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(rs.getByte("id"));
                lesson.setDeleted(rs.getBoolean("isDeleted"));
                lesson.setInsertedDate(rs.getTimestamp("insertedDate"));
                lesson.setLastUpdateDate(rs.getTimestamp("lastUpdateDate"));
                lesson.setName(rs.getString("name"));
                lesson.setCode(rs.getString("code"));
                lesson.setCredit(rs.getByte("credit"));
                lesson.setAvailable(rs.getBoolean("isAvailable"));
                lesson.setLessonType(LessonType.valueOf(rs.getString("lessonType").toUpperCase()));
                lessonList.add(lesson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lessonList;
    }

    @Override
    public boolean existById(Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String select = "SELECT 1 FROM lessons WHERE id = ?";

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
            String count = "SELECT COUNT(*) FROM lessons";
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


    //WRITEABLE


    @Override
    public void add(Lesson obj) {

        String insert = "INSERT INTO lessons " +
                "(name, code, \"credit\", \"isAvailable\",  \"lessonType\" ) " +
                "VALUES (?, ?, ?, ?::\"LessonType\")";

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(insert)) {

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getCode());
            statement.setShort(3, obj.getCredit());
            statement.setBoolean(4, obj.isAvailable());
            statement.setString(5, obj.getLessonType().name());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Lesson obj, Short id) {

        String update = "UPDATE lessons SET " +
                "name = ?, code = ?, credit = ?, \"isAvailable\" = ?, \"lessonType\" = ?::\"LessonType\" " +
                "WHERE id = ?";

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(update)) {

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getCode());
            statement.setShort(3, obj.getCredit());
            statement.setBoolean(4, obj.isAvailable());
            statement.setString(5, obj.getLessonType().name());
            statement.setShort(6, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Short id) {
        try (Connection conn = DaoConnection.getConnection()) {
            String delete = "DELETE FROM lessons WHERE id = ?";
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
            String delete = "DELETE FROM lessons ";

            PreparedStatement statement = conn.prepareStatement(delete);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

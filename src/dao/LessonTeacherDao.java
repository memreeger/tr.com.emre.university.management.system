package dao;

import abst.ILessonTeacher;
import model.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonTeacherDao implements ILessonTeacher {

    private static LessonTeacherDao instance;

    private LessonTeacherDao() {
    }

    public static LessonTeacherDao getInstance() {
        if (instance == null) {
            instance = new LessonTeacherDao();
        }
        return instance;
    }


    @Override
    public List<Lesson> getLessonsByTeacherId(short teacherId) {
        List<Lesson> lessons = new ArrayList<>();

        String sql = """
                SELECT lesson_id
                FROM teachers_lessons
                WHERE teacher_id = ?
                """;

        LessonDao lessonDao = LessonDao.getInstance();

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setShort(1, teacherId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                short lessonId = rs.getShort("lesson_id");
                Lesson lesson = lessonDao.getById(lessonId);

                if (lesson != null) {
                    lessons.add(lesson);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lessons;
    }

    @Override
    public void assignLessonToTeacher(short teacherId, short lessonId) {
        try (Connection conn = DaoConnection.getConnection()) {
            String insert = """
                    INSERT INTO teachers_lessons
                    (teacher_id, lesson_id)
                    VALUES(?,?)
                    """;
            PreparedStatement statement = conn.prepareStatement(insert);
            statement.setInt(1, teacherId);
            statement.setShort(2, lessonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeLessonFromTeacher(short teacherId, short lessonId) {
        String delete = """
                DELETE FROM teachers_lessons
                WHERE teacher_id = ? AND lesson_id = ?
                """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(delete)) {

            statement.setShort(1, teacherId);
            statement.setShort(2, lessonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

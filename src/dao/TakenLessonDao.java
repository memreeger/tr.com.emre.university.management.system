package dao;

import abst.ITakenLesson;
import model.TakenLesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TakenLessonDao implements ITakenLesson {

    private static TakenLessonDao instance;

    private TakenLessonDao() {
    }

    public static TakenLessonDao getInstance() {
        if (instance == null) {
            instance = new TakenLessonDao();
        }
        return instance;
    }


    @Override
    public List<TakenLesson> getByStudentId(int studentId) {
        List<TakenLesson> takenLessons = new ArrayList<>();

        String sql = """
                SELECT *
                FROM students_lessons
                WHERE student_id = ?
                """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TakenLesson takenLesson = new TakenLesson();

                takenLesson.setStudentId(rs.getInt("student_id"));
                takenLesson.setLessonId(rs.getShort("lesson_id"));
                takenLesson.setMidterm(rs.getDouble("midterm"));
                takenLesson.setFinalExam(rs.getDouble("final_exam"));
                takenLesson.setGrade(rs.getDouble("grade"));

                takenLessons.add(takenLesson);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return takenLessons;
    }
}

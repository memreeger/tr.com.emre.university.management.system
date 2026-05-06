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
    public List<TakenLesson> getLessonsByStudentId(int studentId) {
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
                takenLesson.setFinalExam(rs.getDouble("finalExam"));
                takenLesson.setGrade(rs.getDouble("grade"));

                takenLessons.add(takenLesson);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return takenLessons;
    }

    @Override
    public void assignLessonToStudent(int studentId, short lessonId) {
        try (Connection conn = DaoConnection.getConnection()) {
            String insert = """
                    INSERT INTO students_lessons
                    (student_id, lesson_id)
                    VALUES(?,?)
                    """;
            PreparedStatement statement = conn.prepareStatement(insert);
            statement.setInt(1, studentId);
            statement.setShort(2, lessonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMidterm(int studentId, short lessonId, double midterm) {
        try (Connection conn = DaoConnection.getConnection()) {
            String update = """
                    UPDATE students_lessons
                    SET midterm = ?
                    WHERE student_id = ? AND lesson_id = ?
                    """;
            PreparedStatement statement = conn.prepareStatement(update);
            statement.setDouble(1, midterm);
            statement.setInt(2, studentId);
            statement.setShort(3, lessonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateFinal(int studentId, short lessonId, double finalExam) {
        try (Connection conn = DaoConnection.getConnection()) {
            String update = """
                    UPDATE students_lessons
                    SET "finalExam" = ?
                    WHERE student_id = ? AND lesson_id = ?
                    """;

            PreparedStatement statement = conn.prepareStatement(update);
            statement.setDouble(1, finalExam);
            statement.setInt(2, studentId);
            statement.setShort(3, lessonId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGrade(int studentId, short lessonId) {
        String select = """
            SELECT midterm, "finalExam"
            FROM students_lessons
            WHERE student_id = ? AND lesson_id = ?
            """;

        String update = """
            UPDATE students_lessons
            SET grade = ?
            WHERE student_id = ? AND lesson_id = ?
            """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement selectStatement = conn.prepareStatement(select)) {

            selectStatement.setInt(1, studentId);
            selectStatement.setShort(2, lessonId);

            try (ResultSet rs = selectStatement.executeQuery()) {

                if (!rs.next()) {
                    throw new IllegalArgumentException("Student lesson relation not found.");
                }

                double midterm = rs.getDouble("midterm");
                double finalExam = rs.getDouble("finalExam");

                double grade = (0.4 * midterm) + (0.6 * finalExam);

                try (PreparedStatement updateStatement = conn.prepareStatement(update)) {
                    updateStatement.setDouble(1, grade);
                    updateStatement.setInt(2, studentId);
                    updateStatement.setShort(3, lessonId);

                    updateStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeLessonFromStudent(int studentId, short lessonId) {
        String delete = """
                DELETE FROM students_lessons
                WHERE student_id = ? AND lesson_id = ?
                """;

        try (Connection conn = DaoConnection.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(delete)) {

            statement.setInt(1, studentId);
            statement.setShort(2, lessonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

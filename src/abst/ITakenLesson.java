package abst;

import model.TakenLesson;

import java.util.List;

public interface ITakenLesson {
    List<TakenLesson> getLessonsByStudentId(int studentId);

    void assignLessonToStudent(int studentId, short lessonId);

    void updateMidterm(int studentId, short lessonId, double midterm);

    void updateFinal(int studentId, short lessonId, double finalExam);

    void updateGrade(int studentId, short lessonId);

    void removeLessonFromStudent(int studentId, short lessonId);
}

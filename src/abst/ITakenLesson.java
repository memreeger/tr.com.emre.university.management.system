package abst;

import model.TakenLesson;

import java.util.List;

public interface ITakenLesson {
    List<TakenLesson> getByStudentId(int studentId);
    //void assignLessonToStudent(TakenLesson takenLesson);

    //void updateGrade(int studentId, short lessonId, double midterm, double finalExam);

    //void removeLessonFromStudent(int studentId, short lessonId);
}

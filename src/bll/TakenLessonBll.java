package bll;

import abst.ITakenLesson;
import dao.TakenLessonDao;
import model.TakenLesson;

import java.util.List;

public class TakenLessonBll implements ITakenLesson {
    private static TakenLessonBll instance;

    private TakenLessonBll() {
    }

    public static TakenLessonBll getInstance() {
        if (instance == null) {
            instance = new TakenLessonBll();
        }
        return instance;
    }

    TakenLessonDao dao = TakenLessonDao.getInstance();

    @Override
    public List<TakenLesson> getLessonsByStudentId(int studentId) {
        return dao.getLessonsByStudentId(studentId);
    }

    @Override
    public void assignLessonToStudent(int studentId, short lessonId) {
        dao.assignLessonToStudent(studentId, lessonId);

    }

    @Override
    public void updateMidterm(int studentId, short lessonId, double midterm) {
        dao.updateMidterm(studentId, lessonId, midterm);
    }

    @Override
    public void updateFinal(int studentId, short lessonId, double finalExam) {
        dao.updateFinal(studentId, lessonId, finalExam);
    }

    @Override
    public void updateGrade(int studentId, short lessonId) {
        dao.updateGrade(studentId, lessonId);
    }

    @Override
    public void removeLessonFromStudent(int studentId, short lessonId) {
        dao.removeLessonFromStudent(studentId, lessonId);
    }
}

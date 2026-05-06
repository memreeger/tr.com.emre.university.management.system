package bll;

import abst.ILessonTeacher;
import dao.LessonTeacherDao;
import model.Lesson;

import java.util.List;

public class LessonTeacherBll implements ILessonTeacher {

    private static LessonTeacherBll instance;

    private LessonTeacherBll() {
    }

    public static LessonTeacherBll getInstance() {
        if (instance == null) {
            instance = new LessonTeacherBll();
        }
        return instance;
    }

    LessonTeacherDao dao = LessonTeacherDao.getInstance();


    @Override
    public List<Lesson> getLessonsByTeacherId(short teacherId) {
        return dao.getLessonsByTeacherId(teacherId);
    }

    @Override
    public void assignLessonToTeacher(short teacherId, short lessonId) {
        dao.assignLessonToTeacher(teacherId, lessonId);

    }

    @Override
    public void removeLessonFromTeacher(short teacherId, short lessonId) {
        dao.removeLessonFromTeacher(teacherId, lessonId);
    }
}

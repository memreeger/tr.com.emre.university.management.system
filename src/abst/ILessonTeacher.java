package abst;


import model.Lesson;

import java.util.List;

public interface ILessonTeacher<T> {

    List<T> getLessonsByTeacherId(short teacherId);

    void assignLessonToTeacher(short teacherId, short lessonId);

    void removeLessonFromTeacher(short teacherId, short lessonId);

}

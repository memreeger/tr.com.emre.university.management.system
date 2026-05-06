package abst;


import model.Lesson;

import java.util.List;

public interface ILessonTeacher {

    List<Lesson> getLessonsByTeacherId(short teacherId);

    void assignLessonToTeacher(short teacherId, short lessonId);

    void removeLessonFromTeacher(short teacherId, short lessonId);

}

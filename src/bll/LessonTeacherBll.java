package bll;

import abst.ILessonTeacher;
import dao.LessonTeacherDao;
import dto.lessonDto.LessonResponseDto;
import model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonTeacherBll implements ILessonTeacher<LessonResponseDto> {

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
    public List<LessonResponseDto> getLessonsByTeacherId(short teacherId) {
        List<Lesson> teacherLessons = dao.getLessonsByTeacherId(teacherId);
        List<LessonResponseDto> responseDtoList = new ArrayList<>();

        for (Lesson lesson : teacherLessons) {
            responseDtoList.add(toResponseDto(lesson));
        }

        return responseDtoList;
    }

    @Override
    public void assignLessonToTeacher(short teacherId, short lessonId) {
        dao.assignLessonToTeacher(teacherId, lessonId);

    }

    @Override
    public void removeLessonFromTeacher(short teacherId, short lessonId) {
        dao.removeLessonFromTeacher(teacherId, lessonId);
    }

    private LessonResponseDto toResponseDto(Lesson lesson) {
        LessonResponseDto dto = new LessonResponseDto();

        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCode(lesson.getCode());
        dto.setAvailable(lesson.isAvailable());
        dto.setCredit(lesson.getCredit());
        dto.setLessonType(lesson.getLessonType());

        return dto;
    }

    private Lesson toEntity(LessonResponseDto dto) {
        Lesson lesson = new Lesson();

        lesson.setId(dto.getId());
        lesson.setName(dto.getName());
        lesson.setCode(dto.getCode());
        lesson.setAvailable(dto.isAvailable());
        lesson.setCredit(dto.getCredit());
        lesson.setLessonType(dto.getLessonType());

        return lesson;

    }
}

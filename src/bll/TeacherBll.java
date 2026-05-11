package bll;

import abst.Readable;
import abst.TeacherRegistrationUpdatable;
import abst.Writeable;
import dao.DaoConnection;
import dao.LessonTeacherDao;
import dao.TeacherDao;
import dto.lessonDto.LessonResponseDto;
import dto.teacherDto.TeacherRequestDto;
import dto.teacherDto.TeacherResponseDto;
import model.Lesson;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherBll implements Readable<TeacherResponseDto, Short>, Writeable<TeacherRequestDto, Short>, TeacherRegistrationUpdatable<TeacherRequestDto, Short> {

    private static TeacherBll instance;

    private TeacherBll() {
    }

    public static TeacherBll getInstance() {
        if (instance == null) {
            instance = new TeacherBll();
        }
        return instance;
    }

    private final TeacherDao dao = TeacherDao.getInstance();
    private final LessonTeacherDao lessonTeacherDao = LessonTeacherDao.getInstance();


    //READ

    @Override
    public TeacherResponseDto getById(Short id) {
        Teacher teacher = dao.getById(id);

        return toResponseDto(teacher);
    }

    @Override
    public boolean existsById(Short id) {
        return dao.existsById(id);
    }

    @Override
    public List<TeacherResponseDto> getAll() {
        List<Teacher> teachers = dao.getAll();
        List<TeacherResponseDto> responseList = new ArrayList<>();

        for (Teacher teacher : teachers) {
            responseList.add(toResponseDto(teacher));
        }
        return responseList;
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public List<TeacherResponseDto> findAdults() {
        List<Teacher> adultTeachers = dao.findAdults();
        List<TeacherResponseDto> responseList = new ArrayList<>();

        for (Teacher adultTeacher : adultTeachers) {
            responseList.add(toResponseDto(adultTeacher));
        }

        return responseList;
    }


    //WRITE


    @Override
    public void add(TeacherRequestDto obj) {
        Teacher teacher = toEntity(obj);
        dao.add(teacher);
    }

    @Override
    public void update(TeacherRequestDto obj, Short id) {
        Teacher teacher = toEntity(obj);
        dao.update(teacher, id);
    }

    @Override
    public void delete(Short id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }


    @Override
    public void updateRegistrationNumber(Short number, Short id) {
        dao.updateRegistrationNumber(number, id);
    }

    //HELPER
    private Teacher toEntity(TeacherRequestDto dto) {
        Teacher teacher = new Teacher();

        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setIdentityNumber(dto.getIdentityNumber());
        teacher.setBirthDate(dto.getBirthDate());

        teacher.setRegistrationNumber(dto.getRegistrationNumber());
        //teacher.setLessons(dto.getLessonIds());
        return teacher;
    }

    private TeacherResponseDto toResponseDto(Teacher teacher) {
        TeacherResponseDto dto = new TeacherResponseDto();

        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setIdentityNumber(teacher.getIdentityNumber());
        dto.setBirthDate(teacher.getBirthDate());

        dto.setId(teacher.getId());
        dto.setRegistrationNumber(teacher.getRegistrationNumber());

        List<Lesson> lessons = lessonTeacherDao.getLessonsByTeacherId(teacher.getId());
        List<LessonResponseDto> lessonDtos = new ArrayList<>();

        for (Lesson lesson : lessons) {
            lessonDtos.add(toLessonResponseDto(lesson));
        }

        dto.setLessons(lessonDtos);

        return dto;
    }

    private LessonResponseDto toLessonResponseDto(Lesson lesson) {
        LessonResponseDto dto = new LessonResponseDto();

        dto.setId(lesson.getId());
        dto.setName(lesson.getName());
        dto.setCode(lesson.getCode());
        dto.setLessonType(lesson.getLessonType());
        dto.setCredit(lesson.getCredit());
        dto.setAvailable(lesson.isAvailable());

        return dto;
    }
}

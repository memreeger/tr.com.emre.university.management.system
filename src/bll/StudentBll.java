package bll;

import abst.Readable;
import abst.Writeable;
import dao.StudentDao;
import dao.TakenLessonDao;
import dto.lessonDto.LessonResponseDto;
import dto.studentDto.StudentRequestDto;
import dto.studentDto.StudentResponseDto;
import dto.takenLessonDto.TakenLessonResponseDto;
import model.Lesson;
import model.Student;
import model.TakenLesson;

import java.util.ArrayList;
import java.util.List;

public class StudentBll implements Readable<StudentResponseDto, Integer>, Writeable<StudentRequestDto, Integer> {

    private static StudentBll instance;

    private StudentBll() {
    }

    public static StudentBll getInstance() {
        if (instance == null) {
            instance = new StudentBll();
        }
        return instance;
    }

    //private Readable<Student, Integer> dao = StudentDao.getInstance();
    private final StudentDao dao = StudentDao.getInstance();
    private final TakenLessonDao takenLessonDao = TakenLessonDao.getInstance();

    //READ

    @Override
    public StudentResponseDto getById(Integer id) {
        Student student = dao.getById(id);
        return toResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> getAll() {
        List<Student> students = dao.getAll();
        List<StudentResponseDto> responseList = new ArrayList<>();

        for (Student student : students) {
            responseList.add(toResponseDto(student));
        }
        return responseList;
    }

    @Override
    public boolean existsById(Integer id) {
        return dao.existsById(id);
    }

    public List<StudentResponseDto> findAdults() {
        List<Student> adultStudents = dao.findAdults();
        List<StudentResponseDto> responseList = new ArrayList<>();

        for (Student adultStudent : adultStudents) {
            responseList.add(toResponseDto(adultStudent));
        }
        return responseList;
    }

    @Override
    public long count() {
        return dao.count();
    }

    //WRITE

    @Override
    public void add(StudentRequestDto obj) {
        Student student = toEntity(obj);
        dao.add(student);

    }

    @Override
    public void update(StudentRequestDto obj, Integer id) {
        Student student = toEntity(obj);
        dao.update(student, id);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    //HELPER
    private Student toEntity(StudentRequestDto dto) {
        List<TakenLesson> takenLessons = new ArrayList<>();
        Student student = new Student();

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setIdentityNumber(dto.getIdentityNumber());
        student.setBirthDate(dto.getBirthDate());

        student.setSchoolNumber(dto.getSchoolNumber());
        student.setGrade(dto.getGrade());
        //student.setTakenLessons(dto.getTakenLessonIds());

        return student;
    }

    private StudentResponseDto toResponseDto(Student student) {
        StudentResponseDto dto = new StudentResponseDto();

        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setIdentityNumber(student.getIdentityNumber());
        dto.setBirthDate(student.getBirthDate());

        dto.setId(student.getId());
        dto.setSchoolNumber(student.getSchoolNumber());
        dto.setGrade(student.getGrade());
        //dto.setTakenLessons(student.getTakenLessons());

        List<TakenLesson> takenLessons =
                takenLessonDao.getLessonsByStudentId(student.getId());

        List<TakenLessonResponseDto> takenLessonDtos = new ArrayList<>();

        for (TakenLesson takenLesson : takenLessons) {
            takenLessonDtos.add(toTakenLessonResponseDto(takenLesson));
        }

        dto.setTakenLessons(takenLessonDtos);

        return dto;
    }

    private TakenLessonResponseDto toTakenLessonResponseDto(TakenLesson takenLesson) {

        TakenLessonResponseDto dto = new TakenLessonResponseDto();

        dto.setStudentId(takenLesson.getStudentId());

        dto.setLessonId(takenLesson.getLessonId());

        dto.setMidtermExam(takenLesson.getMidterm());

        dto.setFinalExam(takenLesson.getFinalExam());

        double grade =
                (takenLesson.getMidterm() * 0.4)
                        + (takenLesson.getFinalExam() * 0.6);

        dto.setGrade(grade);

        return dto;
    }
}

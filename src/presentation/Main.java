package presentation;

import bll.*;
import dto.lessonDto.LessonRequestDto;
import dto.lessonDto.LessonResponseDto;
import dto.studentDto.StudentRequestDto;
import dto.studentDto.StudentResponseDto;
import dto.takenLessonDto.TakenLessonResponseDto;
import dto.teacherDto.TeacherRequestDto;
import dto.teacherDto.TeacherResponseDto;
import enums.LessonType;

import java.time.LocalDate;
import java.util.List;

public class Main {

    // Reflection yöntemiyle uygulamanın ihtiyacıolan bütüne nesneler uyguama ayağa kalkarken otomatik olarak oluşturulur ve kullanıma sunulursa
    //dependency injection yöntemiyle gerekli yerlerde parametre olarak verilirse nesne oluştrurmaya gerek kalmadanm bütün nesneler işini görebilir
    // bunesne oluştruma ve gerekli yerlere enjekte etmeişi bir framework tarafında yapılırsa adına IoC: Inversion of Control denir.
    // inverssion işin terse dönmesi reflection ve dependency injection yazılımcı tarafından yazılarak fw oluşturuldu
    // hazır bir fw reflection kullanarak bize nesneerli enjekte imkanı verince iş tesrine dönmmüş oldu

    // modern fw hepsinde bu nesnelerrin barındırıldığı bir de IoC container vardır. Uygulama boyunca kullanılacak nesnelerin
    // beklediği yer gibi düşünülebilir.

    public static void main(String[] args) {

        StudentBll studentBll = StudentBll.getInstance();
        TeacherBll teacherBll = TeacherBll.getInstance();
        LessonBll lessonBll = LessonBll.getInstance();
        TakenLessonBll takenLessonBll = TakenLessonBll.getInstance();
        LessonTeacherBll lessonTeacherBll = LessonTeacherBll.getInstance();

        testLessons(lessonBll);
        testStudents(studentBll);
        testTeachers(teacherBll);
        testTakenLessonRelation(studentBll, lessonBll, takenLessonBll);
        testLessonTeacherRelation(teacherBll, lessonBll, lessonTeacherBll);
    }

    private static void testLessons(LessonBll lessonBll) {
        System.out.println("========== LESSON TEST ==========");

        System.out.println("Get all lessons:");
        printLessonList(lessonBll.getAll());

        System.out.println();

        LessonRequestDto requestDto = new LessonRequestDto();
        String code = "test-" + System.currentTimeMillis();

        requestDto.setName("test lesson");
        requestDto.setCode(code);
        requestDto.setLessonType(LessonType.CLASS);
        requestDto.setCredit((byte) 3);
        requestDto.setAvailable(true);

        System.out.println("Add lesson:");
        lessonBll.add(requestDto);
        short createdLessonId = findLessonIdByCode(lessonBll.getAll(), code);
        System.out.println("Created lesson id: " + createdLessonId);

        System.out.println();

        System.out.println("Get lesson by id:");
        System.out.println(lessonBll.getById(createdLessonId));

        System.out.println();

        System.out.println("Lesson exists by id:");
        System.out.println(lessonBll.existById(createdLessonId));

        System.out.println();

        System.out.print("Lesson total count: ");
        System.out.println(lessonBll.count());

        System.out.println();

        LessonRequestDto updateDto = new LessonRequestDto();
        updateDto.setName("updated test lesson");
        updateDto.setCode(code);
        updateDto.setLessonType(LessonType.LAB);
        updateDto.setCredit((byte) 4);
        updateDto.setAvailable(false);

        System.out.println("Update lesson:");
        lessonBll.update(updateDto, createdLessonId);
        System.out.println(lessonBll.getById(createdLessonId));

        System.out.println();

        System.out.println("Delete lesson:");
        lessonBll.delete(createdLessonId);
        System.out.println("Lesson exists after delete:");
        System.out.println(lessonBll.existById(createdLessonId));

        System.out.println();
    }

    private static void testStudents(StudentBll studentBll) {
        System.out.println("========== STUDENT TEST ==========");

        System.out.println("Get all students:");
        printStudentList(studentBll.getAll());

        System.out.println();

        StudentRequestDto requestDto = new StudentRequestDto();
        String identityNumber = "9" + String.valueOf(System.currentTimeMillis()).substring(3, 13);

        requestDto.setFirstName("Test");
        requestDto.setLastName("Student");
        requestDto.setIdentityNumber(identityNumber);
        requestDto.setBirthDate(LocalDate.of(2000, 1, 1));
        requestDto.setSchoolNumber((short) 88);
        requestDto.setGrade(0.0);

        System.out.println("Add student:");
        studentBll.add(requestDto);
        int createdStudentId = findStudentIdByIdentityNumber(studentBll.getAll(), identityNumber);
        System.out.println("Created student id: " + createdStudentId);

        System.out.println();

        System.out.println("Get student by id:");
        System.out.println(studentBll.getById(createdStudentId));

        System.out.println();

        System.out.println("Student exists by id:");
        System.out.println(studentBll.existsById(createdStudentId));

        System.out.println();

        System.out.print("Student total count: ");
        System.out.println(studentBll.count());

        System.out.println();

        System.out.println("Adult students:");
        printStudentList(studentBll.findAdults());

        System.out.println();

        StudentRequestDto updateDto = new StudentRequestDto();
        updateDto.setFirstName("Test");
        updateDto.setLastName("Student");
        updateDto.setIdentityNumber(identityNumber);
        updateDto.setBirthDate(LocalDate.of(2000, 1, 1));
        updateDto.setSchoolNumber((short) 99);
        updateDto.setGrade(0.0);

        System.out.println("Update student:");
        studentBll.update(updateDto, createdStudentId);
        System.out.println(studentBll.getById(createdStudentId));

        System.out.println();

        System.out.println("Delete student:");
        studentBll.delete(createdStudentId);
        System.out.println("Student exists after delete:");
        System.out.println(studentBll.existsById(createdStudentId));

        System.out.println();
    }

    private static void testTeachers(TeacherBll teacherBll) {
        System.out.println("========== TEACHER TEST ==========");

        System.out.println("Get all teachers:");
        printTeacherList(teacherBll.getAll());

        System.out.println();

        TeacherRequestDto requestDto = new TeacherRequestDto();
        String identityNumber = "8" + String.valueOf(System.currentTimeMillis()).substring(3, 13);

        requestDto.setFirstName("Test");
        requestDto.setLastName("Teacher");
        requestDto.setIdentityNumber(identityNumber);
        requestDto.setBirthDate(LocalDate.of(1990, 1, 1));
        requestDto.setRegistrationNumber((short) 77);

        System.out.println("Add teacher:");
        teacherBll.add(requestDto);
        short createdTeacherId = findTeacherIdByIdentityNumber(teacherBll.getAll(), identityNumber);
        System.out.println("Created teacher id: " + createdTeacherId);

        System.out.println();

        System.out.println("Get teacher by id:");
        System.out.println(teacherBll.getById(createdTeacherId));

        System.out.println();

        System.out.println("Teacher exists by id:");
        System.out.println(teacherBll.existsById(createdTeacherId));

        System.out.println();

        System.out.print("Teacher total count: ");
        System.out.println(teacherBll.count());

        System.out.println();

        System.out.println("Adult teachers:");
        printTeacherList(teacherBll.findAdults());

        System.out.println();

        TeacherRequestDto updateDto = new TeacherRequestDto();
        updateDto.setFirstName("Test");
        updateDto.setLastName("Teacher");
        updateDto.setIdentityNumber(identityNumber);
        updateDto.setBirthDate(LocalDate.of(1990, 1, 1));
        updateDto.setRegistrationNumber((short) 78);

        System.out.println("Update teacher:");
        teacherBll.update(updateDto, createdTeacherId);
        System.out.println(teacherBll.getById(createdTeacherId));

        System.out.println();

        System.out.println("Update teacher registration number:");
        teacherBll.updateRegistrationNumber((short) 79, createdTeacherId);
        System.out.println(teacherBll.getById(createdTeacherId));

        System.out.println();

        System.out.println("Delete teacher:");
        teacherBll.delete(createdTeacherId);
        System.out.println("Teacher exists after delete:");
        System.out.println(teacherBll.existsById(createdTeacherId));

        System.out.println();
    }

    private static void testTakenLessonRelation(StudentBll studentBll,
                                                LessonBll lessonBll,
                                                TakenLessonBll takenLessonBll) {
        System.out.println("========== TAKEN LESSON RELATION TEST ==========");

        StudentRequestDto studentDto = new StudentRequestDto();
        String studentIdentityNumber = "7" + String.valueOf(System.currentTimeMillis()).substring(3, 13);

        studentDto.setFirstName("Relation");
        studentDto.setLastName("Student");
        studentDto.setIdentityNumber(studentIdentityNumber);
        studentDto.setBirthDate(LocalDate.of(2001, 1, 1));
        studentDto.setSchoolNumber((short) 55);
        studentDto.setGrade(0.0);

        studentBll.add(studentDto);
        int studentId = findStudentIdByIdentityNumber(studentBll.getAll(), studentIdentityNumber);

        LessonRequestDto lessonDto = new LessonRequestDto();
        String lessonCode = "rel-stu-" + System.currentTimeMillis();

        lessonDto.setName("relation student lesson");
        lessonDto.setCode(lessonCode);
        lessonDto.setLessonType(LessonType.CLASS);
        lessonDto.setCredit((byte) 3);
        lessonDto.setAvailable(true);

        lessonBll.add(lessonDto);
        short lessonId = findLessonIdByCode(lessonBll.getAll(), lessonCode);

        System.out.println("Assign lesson to student:");
        takenLessonBll.assignLessonToStudent(studentId, lessonId);
        System.out.println("Lesson assigned successfully.");

        System.out.println();

        System.out.println("Lessons by student before grade update:");
        printTakenLessonList(takenLessonBll.getLessonsByStudentId(studentId));

        System.out.println();

        System.out.println("Update midterm:");
        takenLessonBll.updateMidterm(studentId, lessonId, 80);
        System.out.println("Midterm updated.");

        System.out.println();

        System.out.println("Update final:");
        takenLessonBll.updateFinal(studentId, lessonId, 90);
        System.out.println("Final updated.");

        System.out.println();

        System.out.println("Calculate grade:");
        takenLessonBll.updateGrade(studentId, lessonId);
        System.out.println("Grade calculated.");

        System.out.println();

        System.out.println("Lessons by student after grade update:");
        printTakenLessonList(takenLessonBll.getLessonsByStudentId(studentId));

        System.out.println();

        System.out.println("Remove lesson from student:");
        takenLessonBll.removeLessonFromStudent(studentId, lessonId);
        System.out.println("Lesson removed from student.");

        System.out.println();

        System.out.println("Lessons by student after remove:");
        printTakenLessonList(takenLessonBll.getLessonsByStudentId(studentId));

        System.out.println();

        studentBll.delete(studentId);
        lessonBll.delete(lessonId);

        System.out.println("Temporary student and lesson deleted.");
        System.out.println();
    }

    private static void testLessonTeacherRelation(TeacherBll teacherBll,
                                                  LessonBll lessonBll,
                                                  LessonTeacherBll lessonTeacherBll) {
        System.out.println("========== LESSON TEACHER RELATION TEST ==========");

        TeacherRequestDto teacherDto = new TeacherRequestDto();
        String teacherIdentityNumber = "6" + String.valueOf(System.currentTimeMillis()).substring(3, 13);

        teacherDto.setFirstName("Relation");
        teacherDto.setLastName("Teacher");
        teacherDto.setIdentityNumber(teacherIdentityNumber);
        teacherDto.setBirthDate(LocalDate.of(1988, 1, 1));
        teacherDto.setRegistrationNumber((short) 66);

        teacherBll.add(teacherDto);
        short teacherId = findTeacherIdByIdentityNumber(teacherBll.getAll(), teacherIdentityNumber);

        LessonRequestDto lessonDto = new LessonRequestDto();
        String lessonCode = "rel-tea-" + System.currentTimeMillis();

        lessonDto.setName("relation teacher lesson");
        lessonDto.setCode(lessonCode);
        lessonDto.setLessonType(LessonType.LAB);
        lessonDto.setCredit((byte) 2);
        lessonDto.setAvailable(true);

        lessonBll.add(lessonDto);
        short lessonId = findLessonIdByCode(lessonBll.getAll(), lessonCode);

        System.out.println("Assign lesson to teacher:");
        lessonTeacherBll.assignLessonToTeacher(teacherId, lessonId);
        System.out.println("Lesson assigned to teacher successfully.");

        System.out.println();

        System.out.println("Lessons by teacher:");
        printLessonList(lessonTeacherBll.getLessonsByTeacherId(teacherId));

        System.out.println();

        System.out.println("Remove lesson from teacher:");
        lessonTeacherBll.removeLessonFromTeacher(teacherId, lessonId);
        System.out.println("Lesson removed from teacher.");

        System.out.println();

        System.out.println("Lessons by teacher after remove:");
        printLessonList(lessonTeacherBll.getLessonsByTeacherId(teacherId));

        System.out.println();

        teacherBll.delete(teacherId);
        lessonBll.delete(lessonId);

        System.out.println("Temporary teacher and lesson deleted.");
        System.out.println();
    }

    private static short findLessonIdByCode(List<LessonResponseDto> lessons, String code) {
        for (LessonResponseDto lesson : lessons) {
            if (lesson.getCode().equals(code)) {
                return lesson.getId();
            }
        }

        throw new IllegalArgumentException("Lesson not found by code: " + code);
    }

    private static int findStudentIdByIdentityNumber(List<StudentResponseDto> students, String identityNumber) {
        for (StudentResponseDto student : students) {
            if (student.getIdentityNumber().equals(identityNumber)) {
                return student.getId();
            }
        }

        throw new IllegalArgumentException("Student not found by identity number: " + identityNumber);
    }

    private static short findTeacherIdByIdentityNumber(List<TeacherResponseDto> teachers, String identityNumber) {
        for (TeacherResponseDto teacher : teachers) {
            if (teacher.getIdentityNumber().equals(identityNumber)) {
                return (short) teacher.getId();
            }
        }

        throw new IllegalArgumentException("Teacher not found by identity number: " + identityNumber);
    }

    private static void printStudentList(List<StudentResponseDto> list) {
        for (StudentResponseDto studentDto : list) {
            System.out.println(studentDto);
        }
    }

    private static void printTeacherList(List<TeacherResponseDto> list) {
        for (TeacherResponseDto teacher : list) {
            System.out.println(teacher);
        }
    }

    private static void printLessonList(List<LessonResponseDto> list) {
        for (LessonResponseDto lesson : list) {
            System.out.println(lesson);
        }
    }

    private static void printTakenLessonList(List<TakenLessonResponseDto> list) {
        for (TakenLessonResponseDto takenLesson : list) {
            System.out.println(takenLesson);
        }
    }
}
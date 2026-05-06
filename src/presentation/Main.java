package presentation;

import abst.ILessonTeacher;
import abst.ITakenLesson;
import abst.LessonReadable;
import abst.Readable;
import bll.*;
import model.Lesson;
import model.Student;
import model.TakenLesson;
import model.Teacher;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Readable<Student, Integer> studentBll = StudentBll.getInstance();
        Readable<Teacher, Short> teacherBll = TeacherBll.getInstance();
        LessonReadable<Lesson, Short> lessonBll = LessonBll.getInstance();
        ITakenLesson takenLessonBll = TakenLessonBll.getInstance();
        ILessonTeacher lessonTeacherBll = LessonTeacherBll.getInstance();


        testStudents(studentBll);
        testTeachers(teacherBll);
        testLessons(lessonBll);
        testTakenLessons(takenLessonBll);
        testLessonTeacher(lessonTeacherBll);
    }

    private static void testLessonTeacher(ILessonTeacher lessonTeacherBll) {
        System.out.println("========== LESSON TEACHER TEST ==========");

        short teacherId = 2;
        short lessonId = 1;

        System.out.println("Assign lesson to teacher:");
        try {
            lessonTeacherBll.assignLessonToTeacher(teacherId, lessonId);
            System.out.println("Lesson assigned to teacher successfully.");
        } catch (RuntimeException e) {
            System.out.println("Assign failed or already exists: " + e.getMessage());
        }

        System.out.println();

        System.out.println("Lessons by teacher:");
        printLessonList(lessonTeacherBll.getLessonsByTeacherId(teacherId));

        System.out.println();

        // System.out.println("Remove lesson from teacher:");
        // lessonTeacherBll.removeLessonsFromTeacher(teacherId, lessonId);
        // System.out.println("Lesson removed from teacher.");

        // System.out.println();
        // System.out.println("Lessons by teacher after remove:");
        // printLessonList(lessonTeacherBll.getLessonsByTeacherId(teacherId));

        System.out.println();
    }

    private static void testStudents(Readable<Student, Integer> studentBll) {
        System.out.println("========== STUDENT TEST ==========");

        System.out.println("Get all students:");
        printStudentList(studentBll.getAll());

        System.out.println();

        System.out.println("Get student by id:");
        System.out.println(studentBll.getById(2));

        System.out.println();

        System.out.println("Student exists by id:");
        System.out.println(studentBll.existsById(2));

        System.out.println();

        System.out.print("Student total count: ");
        System.out.println(studentBll.count());

        System.out.println();
    }

    private static void testTeachers(Readable<Teacher, Short> teacherBll) {
        System.out.println("========== TEACHER TEST ==========");

        System.out.println("Get all teachers:");
        printTeacherList(teacherBll.getAll());

        System.out.println();

        System.out.println("Get teacher by id:");
        System.out.println(teacherBll.getById((short) 2));

        System.out.println();

        System.out.println("Teacher exists by id:");
        System.out.println(teacherBll.existsById((short) 2));

        System.out.println();

        System.out.print("Teacher total count: ");
        System.out.println(teacherBll.count());

        System.out.println();
    }

    private static void testLessons(LessonReadable<Lesson, Short> lessonBll) {
        System.out.println("========== LESSON TEST ==========");

        System.out.println("Get all lessons:");
        printLessonList(lessonBll.getAll());

        System.out.println();

        System.out.println("Get lesson by id:");
        System.out.println(lessonBll.getById((short) 1));

        System.out.println();

        System.out.println("Lesson exists by id:");
        System.out.println(lessonBll.existById((short) 1));

        System.out.println();

        System.out.print("Lesson total count: ");
        System.out.println(lessonBll.count());

        System.out.println();
    }

    private static void testTakenLessons(ITakenLesson takenLessonBll) {
        System.out.println("========== TAKEN LESSON TEST ==========");

        int studentId = 2;
        short lessonId = 1;

        System.out.println("Assign lesson to student:");
        try {
            takenLessonBll.assignLessonToStudent(studentId, lessonId);
            System.out.println("Lesson assigned successfully.");
        } catch (RuntimeException e) {
            System.out.println("Assign failed or already exists: " + e.getMessage());
        }

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


        // takenLessonBll.removeLessonFromStudent(studentId, lessonId);
        // System.out.println("Lesson removed from student.");
    }

    private static void printStudentList(List<Student> list) {
        for (Student student : list) {
            System.out.println(student);
        }
    }

    private static void printTeacherList(List<Teacher> list) {
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }

    private static void printLessonList(List<Lesson> list) {
        for (Lesson lesson : list) {
            System.out.println(lesson);
        }
    }

    private static void printTakenLessonList(List<TakenLesson> list) {
        for (TakenLesson takenLesson : list) {
            System.out.println(takenLesson);
        }
    }
}
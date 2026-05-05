package presentation;

import abst.LessonReadable;
import abst.Readable;
import bll.LessonBll;
import bll.StudentBll;
import bll.TeacherBll;
import model.Lesson;
import model.Student;
import model.Teacher;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Readable<Student, Integer> studentBll = StudentBll.getInstance();
        Readable<Teacher, Short> teacherBll = TeacherBll.getInstance();
        LessonReadable<Lesson, Short> lessonBll = LessonBll.getInstance();

        testStudents(studentBll);
        testTeachers(teacherBll);
        testLessons(lessonBll);
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
}
package presentation;

import abst.Readable;
import bll.StudentBll;
import model.Student;
import model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

// TEACHER DB OLUŞTUR
        Readable<Student, Integer> bll = StudentBll.getInstance();

        System.out.println("get all");
        //List<Student> studentList = bll.getAll();
        printList(bll.getAll());
        System.out.println();

        System.out.println("Get by id");
        System.out.println(bll.getById(2));
        System.out.println();

        System.out.println("find by age greater than 18");
        printList(bll.findAdults());

        System.out.print("total count : ");
        System.out.println(bll.count());

    }

    public static void printList(List<Student> list) {
        for (Student s : list) {
            System.out.println(s);
        }
    }
}
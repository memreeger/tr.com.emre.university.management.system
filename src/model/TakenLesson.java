package model;

import lombok.Data;

@Data
public class TakenLesson {
    private Lesson lesson;
    private double midterm;
    private double finalExam;
    private double grade;
}

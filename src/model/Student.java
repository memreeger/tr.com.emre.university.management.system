package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class Student extends Person<Integer> {
    private short schoolNumber;
    private double grade;
    private List<TakenLesson> takenLessons;

}

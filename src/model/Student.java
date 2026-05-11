package model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Student extends Person<Integer> {
    private short schoolNumber;
    private double grade;
    private List<TakenLesson> takenLessons;

}

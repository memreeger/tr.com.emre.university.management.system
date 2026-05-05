package model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student extends Person<Integer> {
    private short schoolNumber;
    private double grade;
    private List<TakenLesson> takenLessons;






}

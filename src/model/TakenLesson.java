package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(callSuper = true)
public class TakenLesson {
    private int studentId;
    private short lessonId;
    private double midterm;
    private double finalExam;
    private double grade;

    @Override
    public String toString() {
        return "TakenLesson{" +
                "studentId=" + studentId +
                ", lessonId=" + lessonId +
                ", midterm=" + midterm +
                ", finalExam=" + finalExam +
                ", grade=" + grade +
                '}';
    }
}

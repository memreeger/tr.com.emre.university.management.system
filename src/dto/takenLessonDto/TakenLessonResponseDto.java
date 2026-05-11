package dto.takenLessonDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TakenLessonResponseDto {


    private Integer studentId;

    private short lessonId;

    private double midtermExam;
    private double finalExam;
    private double grade;

    @Override
    public String toString() {
        return "TakenLessonResponseDto{" +
                "studentId=" + studentId +
                ", lessonId=" + lessonId +
                ", midtermExam=" + midtermExam +
                ", finalExam=" + finalExam +
                ", grade=" + grade +
                '}';
    }
}

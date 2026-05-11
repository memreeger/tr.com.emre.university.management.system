package dto.takenLessonDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TakenLessonRequestDto {
    private int studentId;
    private short lessonId;
    private double midterm;
    private double finalExam;
}

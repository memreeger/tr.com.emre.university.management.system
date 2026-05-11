package dto.studentDto;

import dto.baseDto.BasePersonDto;
import dto.takenLessonDto.TakenLessonResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto extends BasePersonDto {

    private int id;
    private short schoolNumber;
    private double grade;
    private List<TakenLessonResponseDto> takenLessons;

    @Override
    public String toString() {
        return "StudentResponseDto{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", schoolNumber=" + getSchoolNumber() +
                ", grade=" + getGrade() +
                ", takenLessons=" + getTakenLessons() +
                '}';
    }
}
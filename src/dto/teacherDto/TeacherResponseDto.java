package dto.teacherDto;

import dto.baseDto.BasePersonDto;
import dto.lessonDto.LessonResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDto extends BasePersonDto {
    private int id;
    private short registrationNumber;
    private List<LessonResponseDto> lessons;

    @Override
    public String toString() {
        return "TeacherResponseDto{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", registrationNumber=" + getRegistrationNumber() +
                ", lessons=" + getLessons() +
                '}';
    }
}

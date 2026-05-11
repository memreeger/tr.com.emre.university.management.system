package dto.studentDto;

import dto.baseDto.BasePersonDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentRequestDto extends BasePersonDto {

    private short schoolNumber;
    private double grade;
    private List<Integer> takenLessonIds;
}
package dto.teacherDto;

import dto.baseDto.BasePersonDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TeacherRequestDto extends BasePersonDto {
    private short registrationNumber;
    private List<Integer> lessonIds;
}

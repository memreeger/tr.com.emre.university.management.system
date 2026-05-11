package dto.lessonDto;

import dto.baseDto.BaseLessonDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class LessonRequestDto extends BaseLessonDto {

}

package dto.baseDto;

import enums.LessonType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class BaseLessonDto {
    private String name;
    private String code;
    private LessonType lessonType;
    private byte credit;
    private boolean isAvailable;
}

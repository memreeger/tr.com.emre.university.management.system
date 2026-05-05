package model;

import enums.LessonType;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Lesson extends BaseClass<Byte> {
    private String name;
    private String code;
    private LessonType lessonType;
    private byte credit;
    private boolean isAvailable;

}

package dto.lessonDto;

import dto.baseDto.BaseLessonDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LessonResponseDto extends BaseLessonDto {

    private short id;

    @Override
    public String toString() {
        return "LessonResponseDto{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", code='" + getCode() + '\'' +
                ", type=" + getLessonType() +
                ", credit=" + getCredit() +
                ", available=" + isAvailable() +
                '}';
    }
}
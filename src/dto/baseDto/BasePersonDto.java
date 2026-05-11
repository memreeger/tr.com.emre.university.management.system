package dto.baseDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class BasePersonDto {
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate birthDate;

}

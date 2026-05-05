package model;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Person<T> extends BaseClass<T>{
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate birthDate;


}

package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person<T> extends BaseClass<T>{
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate birthDate;



}

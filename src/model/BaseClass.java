package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseClass<T> {
    private T id;
    private boolean isDeleted;
    private LocalDateTime insertedDate;
    private LocalDateTime lastUpdateDate;


}

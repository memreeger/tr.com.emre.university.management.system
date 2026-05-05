package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseClass<T> {
    private T id;
    private boolean isDeleted;
    private Timestamp insertedDate;
    private Timestamp lastUpdateDate;


}

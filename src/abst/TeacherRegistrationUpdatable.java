package abst;

import java.util.Optional;

public interface TeacherRegistrationUpdatable<T, K> {
    //Optional<T> updateRegistrationNumber(Short number, K id); // değer oladabilir olmayadabilir!!!
    void updateRegistrationNumber(Short number, K id);
}
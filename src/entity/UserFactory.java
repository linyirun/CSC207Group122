package entity;

import java.time.LocalDateTime;

public class UserFactory {
    public User create(String name, String password, LocalDateTime ltd) {
        return new User(name, password, ltd);
    }
}

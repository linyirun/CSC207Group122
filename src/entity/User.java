package entity;

import java.time.LocalDateTime;

public class User {
    private String name;
    private String password;
    private LocalDateTime ltd;

    public User(String name, String password, LocalDateTime ltd) {
        this.name = name;
        this.password = password;
        this.ltd = ltd;

    }
    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public LocalDateTime getCreationTime() {
        return this.ltd;
    }

}

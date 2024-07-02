package app.api.user;


import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "user")
public class User {
    @Id
    public String id;
    public String name;
    public String surname;
    public String username;
    public String password;
    public int role;
    public Binary image;

    private User(String username, String password, String name, String surname, Integer role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = Objects.requireNonNullElse(role, 0);
    }

    public static User fromDto(User dto) {
        return new User(dto.username, dto.password, dto.name, dto.surname, null);
    }
}

//    public User(String username, String password, Integer role) {
//        System.out.println(2);
//        this.username = username;
//        this.password = password;
//        this.role = Role.values()[role];
//    }

package app.api.auth;

import app.api.session.Session;
import app.api.user.User;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

//public class AuthResponse {
//    public User user;
//    public Session session;
//
//    AuthResponse(User user, Session session) {
//        this.user = user;
//        this.session = session;
//    }
//}


public class AuthResponse {
    public String userId;
    public String name;
    public String surname;
    public String username;
    public String password;
    public int role;

    public String sessionId;
    public String session;
    public String user;


    AuthResponse(User user, Session session) {
        userId = user.id;
        name = user.name;
        surname = user.surname;
        username = user.username;
        password = user.password;
        role = user.role;
        sessionId = session.id;
        this.session = session.session;
        this.user = session.user;
    }

//    AuthResponse(User user, Session session) {
//        this.user = user;
//        this.session = session;
//    }
}

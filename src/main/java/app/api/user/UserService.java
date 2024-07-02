package app.api.user;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private final MongoTemplate mongoTemplate;

    UserService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    List<User> all() {
        Query query = new Query();
        query.addCriteria(Criteria.where("role").ne(2));
        return mongoTemplate.find(query, User.class);
    }

    User getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }


    public User create(User dto) {
        User user = User.fromDto(dto);
        return mongoTemplate.insert(user, "user");
    }

    User uploadImage(String id, MultipartFile file) throws IOException {
        User user = getById(id);
        if (user == null) {
            return null;
        }

        user.image = new Binary(file.getBytes());
        return mongoTemplate.save(user);
    }


    User upToModerator(String id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }

        user.role = 1;
        return mongoTemplate.save(user);
    }

    User downToVisitor(String id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }

        user.role = 0;
        return mongoTemplate.save(user);
    }

    DeleteResult delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, User.class);
    }
}

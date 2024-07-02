package app.api.city;

import app.api.user.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final MongoTemplate mongoTemplate;

    CityService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    List<City> all() {
        return mongoTemplate.findAll(City.class);
    }

    User findByApiId(String apiId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("apiId").is(apiId));
        return mongoTemplate.findOne(query, User.class);
    }

    City create(City dto) {
        City city = new City(dto.name, dto.apiId);
        return mongoTemplate.insert(city, "city");
    }

    DeleteResult delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, City.class);
    }
}

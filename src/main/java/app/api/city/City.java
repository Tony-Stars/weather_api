package app.api.city;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "city")
public class City {
    @Id
    public String id;
    public String name;
    public String apiId;

    City(String name, String apiId) {
        this.name = name;
        this.apiId = apiId;
    }
}

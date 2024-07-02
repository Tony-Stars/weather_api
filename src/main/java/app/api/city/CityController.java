package app.api.city;

import app.api.city.City;
import app.api.city.CityService;
import app.api.session.SessionService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    SessionService sessionService;

    @GetMapping
    ResponseEntity<List<City>> all(@RequestHeader String sessionId) {
        try {
            if (sessionService.findBySession(sessionId) == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(cityService.all());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    ResponseEntity<City> add(@RequestHeader String sessionId, @RequestBody City dto) {
        if (sessionService.findBySession(sessionId) == null) {
            return ResponseEntity.badRequest().build();
        }

        if (cityService.findByApiId(dto.apiId) != null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(cityService.create(dto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@RequestHeader String sessionId, @PathVariable("id") String id) {
        if (sessionService.findBySession(sessionId) == null) {
            return ResponseEntity.badRequest().build();
        }

        DeleteResult result = cityService.delete(id);
        return ResponseEntity.ok().body(result.wasAcknowledged());
    }
}

package app.api.user;

import app.api.session.SessionService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;

    @GetMapping
    ResponseEntity<List<User>> all(@RequestHeader String sessionId) {
        try {
            if (sessionService.findBySession(sessionId) == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(userService.all());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/image/{userId}")
    ResponseEntity<User> imageUpload(
            @PathVariable("userId") String userId,
            @RequestParam("file") MultipartFile file,
            @RequestHeader String sessionId
    ) {
        try {
            if (sessionService.findBySession(sessionId) == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(userService.uploadImage(userId, file));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/up/{userId}")
    ResponseEntity<User> upToModerator(@RequestHeader String sessionId, @PathVariable("userId") String userId) {
        try {
            if (sessionService.findBySession(sessionId) == null) {
                return ResponseEntity.badRequest().build();
            }
            User user = userService.upToModerator(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/down/{userId}")
    ResponseEntity<User> downToVisitor(@RequestHeader String sessionId, @PathVariable("userId") String userId) {
        try {
            if (sessionService.findBySession(sessionId) == null) {
                return ResponseEntity.badRequest().build();
            }
            User user = userService.downToVisitor(userId);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@RequestHeader String sessionId, @PathVariable("id") String id) {
        if (sessionService.findBySession(sessionId) == null) {
            return ResponseEntity.badRequest().build();
        }

        DeleteResult result = userService.delete(id);
        return ResponseEntity.ok().body(result.wasAcknowledged());
    }
}

package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {
    @PostMapping("/shutdown")
    public ResponseEntity<String> shutdownApp() {
        return ResponseEntity.ok().build();
    }
}

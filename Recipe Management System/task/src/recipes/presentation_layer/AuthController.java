package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.business_layer.AuthService;
import recipes.business_layer.domain.User;
import recipes.business_layer.dto.RegistrationRequestDTO;
import recipes.exceptions.CustomExceptions;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationDTO, BindingResult bindingResult) {
        // check for body parameter errors before passing the request dto to auth service
        if (bindingResult.hasErrors()) throw new CustomExceptions.RegistrationParametersInvalidException();

        System.out.println("Registering...");
        User user = authService.registerUser(registrationDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/userInfo")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal UserDetails details) {
        if (details == null) return ResponseEntity.status(401).build();
        User user = authService.getUser(details.getUsername());
        if (user == null) {
            System.err.println("User null!");
            return ResponseEntity.status(401).build();
        }

        System.out.println(user);

        return ResponseEntity.ok("Logged in as: %s".formatted(user.getEmail()));
    }


}

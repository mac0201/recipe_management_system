package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recipes.business_layer.AuthService;
import recipes.business_layer.domain.User;
import recipes.business_layer.dto.RegistrationRequestDTO;
import recipes.exceptions.CustomExceptions;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationDTO, BindingResult bindingResult) {
        // check for body parameter errors before passing the request dto to auth service
        if (bindingResult.hasErrors()) throw new CustomExceptions.RegistrationParametersInvalidException();
        User user = authService.registerUser(registrationDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal UserDetails details) {
        if (details == null) return ResponseEntity.status(401).build();
        User user = authService.findUser(details.getUsername());
        if (user == null) return ResponseEntity.status(401).build();
        System.out.println(user);
        return ResponseEntity.ok("Logged in as: \n%s".formatted(user));
    }
}

package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.business_layer.AuthService;
import recipes.business_layer.AuthService.UserAdapter;
import recipes.business_layer.domain.User;

import java.util.List;

@RestController
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {

        User user = new User();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setAuthorities(List.of("ROLE_USER"));
        authService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/userInfo")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal UserDetails details) {
        if (details == null) return ResponseEntity.status(401).build();
        User user = authService.getUser(details.getUsername());
        return ResponseEntity.ok("Logged in as: %s".formatted(user));
    }

    record RegistrationRequest(String email, String password) { }

}

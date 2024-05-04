package recipes.business_layer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegistrationRequestDTO(@Email String email, @NotBlank @Size(min = 8) String password) {
}

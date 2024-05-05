package recipes.business_layer.dto;

import javax.validation.constraints.*;

public record RegistrationRequestDTO(
        @NotNull @Email @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @NotBlank @Size(min = 8) String password
) { }

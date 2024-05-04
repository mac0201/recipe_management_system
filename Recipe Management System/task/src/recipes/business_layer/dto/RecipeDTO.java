package recipes.business_layer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.business_layer.domain.User;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private long recipeId;
    private String user;
    @NotBlank private String name;
    @NotBlank private String category;
    @NotBlank private String description;
    private LocalDateTime date;
    @NotEmpty @Size(min = 1)
    private List<String> ingredients;
    @NotEmpty @Size(min = 1)
    private List<String> directions;
}

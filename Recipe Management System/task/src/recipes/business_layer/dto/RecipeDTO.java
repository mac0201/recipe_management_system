package recipes.business_layer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    @NotBlank private String name;
    @NotBlank private String description;
    @NotEmpty @Size(min = 1)
    private List<String> ingredients;
    @NotEmpty @Size(min = 1)
    private List<String> directions;
}

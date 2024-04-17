package recipes.business_layer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    @NotEmpty private String name;
    @NotEmpty private String description;
    @NotEmpty private String ingredients;
    @NotEmpty private String directions;
}

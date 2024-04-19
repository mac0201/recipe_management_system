package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.business_layer.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<AddResponseDTO> addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.addRecipe(recipeDTO));
    }

    @GetMapping()
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.getRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok().body(recipeService.getRecipeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

}

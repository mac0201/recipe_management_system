package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.business_layer.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipe")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.getRecipes());
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok().body(recipeService.getRecipeById(id));
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<AddResponseDTO> addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.addRecipe(recipeDTO));
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/actuator/shutdown")
    public ResponseEntity<String> shutdown() {
        return ResponseEntity.ok().build();
    }

}

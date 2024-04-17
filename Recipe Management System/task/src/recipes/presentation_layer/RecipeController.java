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

    @GetMapping("/recipe")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.getRecipes());
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable int id) {
        return ResponseEntity.ok().body(recipeService.getRecipeById(id));
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<AddResponseDTO> addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.addRecipe(recipeDTO));
    }

}

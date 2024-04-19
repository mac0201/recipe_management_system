package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.business_layer.RecipeService;
import recipes.exceptions.CustomExceptions;

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

    @PostMapping("/new/multiple")
    public ResponseEntity<AddResponseDTO> addRecipe(@Valid @RequestBody List<RecipeDTO> recipeDTO) {
        return ResponseEntity.ok().body(recipeService.addMultipleRecipes(recipeDTO));
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
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    // Stage 4
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> findRecipesByNameOrCategory(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        // validate parameters
        if ((name == null && category == null) || (name != null && category != null)) {
            throw new CustomExceptions.InvalidSearchParameterException();
        }
        // if name passed -> search by name
        if (name != null) return ResponseEntity.ok(recipeService.getRecipesByName(name));
        else return ResponseEntity.ok(recipeService.getRecipesByCategory(category));
    }

}

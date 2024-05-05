package recipes.presentation_layer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.business_layer.RecipeService;
import recipes.exceptions.CustomExceptions;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Add new recipe
    @PostMapping("/new")
    public ResponseEntity<AddResponseDTO> addRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok().body(recipeService.addRecipe(recipeDTO));
    }

    // Get list of all recipes
    @GetMapping()
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.findAllRecipes());
    }

    // Get recipe with specified id
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable long id) {
        return ResponseEntity.ok().body(recipeService.findRecipeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) throws AccessDeniedException {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    // Stage 4
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable long id, @Valid @RequestBody RecipeDTO recipeDTO) throws AccessDeniedException {
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
        if (name != null) return ResponseEntity.ok(recipeService.findRecipesByName(name));
        else return ResponseEntity.ok(recipeService.findRecipesByCategory(category));
    }

    @GetMapping("/user")
    public ResponseEntity<List<RecipeDTO>> getCurrentUserRecipes(@AuthenticationPrincipal UserDetails details) {
        // username == email
        return ResponseEntity.ok(recipeService.findRecipesFromCurrentUser(details.getUsername()));
    }
}

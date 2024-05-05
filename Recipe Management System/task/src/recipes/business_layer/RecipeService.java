package recipes.business_layer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;
import recipes.persistence_layer.RecipeRepository;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Mapper mapper;

    public RecipeService(RecipeRepository recipeRepository, Mapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.mapper = modelMapper;
    }

    @Transactional
    public AddResponseDTO addRecipe(RecipeDTO recipeDTO) {
        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Map the DTO into Recipe object and set required fields
        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setDate(LocalDateTime.now());
        recipe.setUserEmail(auth.getName());
        // Save recipe
        recipe = this.recipeRepository.save(recipe);
        return new AddResponseDTO(recipe.getRecipeId());
    }

    public List<RecipeDTO> findAllRecipes() {
        // Retrieve all recipes, map each into a RecipeDTO object and return in a list
        return mapper.mapAll(recipeRepository.findAll(), RecipeDTO.class);
    }

    public RecipeDTO findRecipeById(long id) {
        // Find recipe with given id. If found map to RecipeDTO object, else throw exception.
        return mapper.map(
                recipeRepository.findById(id)
                        .orElseThrow(CustomExceptions.RecipeNotFoundException::new),
                RecipeDTO.class);
    }

    @Transactional
    public void deleteRecipe(long id) throws AccessDeniedException {
        // Retrieve and delete recipe with given id if current user is the author
        Recipe recipe = verifyIdentityAndGetRecipe(id);
        recipeRepository.delete(recipe);
    }

    // Update recipe with given id
    public void updateRecipe(long id, RecipeDTO recipeDTO) throws AccessDeniedException {
        // Retrieve and update recipe with given id if current user is the author
        Recipe recipe = verifyIdentityAndGetRecipe(id);
        // update fields
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setCategory(recipeDTO.getCategory());
        recipe.setDate(LocalDateTime.now());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setDirections(recipeDTO.getDirections());
        recipeRepository.save(recipe);
    }

    @Transactional
    public List<RecipeDTO> findRecipesByName(String name) {
        return recipeRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    @Transactional
    public List<RecipeDTO> findRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    @Transactional
    public List<RecipeDTO> findRecipesFromCurrentUser(String email) {
        return recipeRepository.findAllByUserEmail(email)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    // Retrieves recipe with the given id and verifies current user is the author
    private Recipe verifyIdentityAndGetRecipe(long id) throws AccessDeniedException {
        // Retrieve Optional containing recipe with given id, else throw exception
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(CustomExceptions.RecipeNotFoundException::new);
        // Get auth context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Verify current user is author
        if (!recipe.getUserEmail().equals(auth.getName()))
            throw new AccessDeniedException("Only the author can modify this recipe.");
        return recipe;
    }
}

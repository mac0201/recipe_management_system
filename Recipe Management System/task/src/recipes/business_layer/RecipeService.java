package recipes.business_layer;

import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;
import recipes.persistence_layer.RecipeRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Mapper mapper;

    public RecipeService(RecipeRepository recipeRepository, Mapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.mapper = modelMapper;
    }

    public AddResponseDTO addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe = this.recipeRepository.save(recipe);
        return new AddResponseDTO(recipe.getId());
    }

    public List<RecipeDTO> getRecipes() {
        // Finds all Recipe objects in the database and returns them as a List of RecipeDTO objects
        return mapper.mapAll(recipeRepository.findAll(), RecipeDTO.class);
    }

    public RecipeDTO getRecipeById(long id) {
        // Find Optional<Recipe> by id and throw exception if it does not exist, otherwise map the result to RecipeDTO
        return mapper.map(
                recipeRepository.findById(id)
                        .orElseThrow(CustomExceptions.RecipeNotFoundException::new),
                RecipeDTO.class);
    }

    @Transactional
    public RecipeDTO deleteRecipe(long id) {
        // Find Optional<Recipe> by id and throw exception if it does not exist, otherwise delete recipe from database,
        // and return a mapped RecipeDTO object
        Recipe recipe = recipeRepository
                .findById(id)
                .orElseThrow(CustomExceptions.RecipeNotFoundException::new);
        recipeRepository.delete(recipe);
        return mapper.map(recipe, RecipeDTO.class);
    }

}

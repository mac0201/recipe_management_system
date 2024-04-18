package recipes.business_layer;

import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;
import recipes.persistence_layer.RecipeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        return mapper.mapAll(recipeRepository.findAll(), RecipeDTO.class);
    }

    public RecipeDTO getRecipeById(long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        // throw exception if recipe does not exist
        return mapper.map(recipe.orElseThrow(CustomExceptions.RecipeNotFoundException::new), RecipeDTO.class);
    }

    @Transactional
    public RecipeDTO deleteRecipe(long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        // throw exception if recipe does not exist
        Recipe recipe =  recipeOptional.orElseThrow(CustomExceptions.RecipeNotFoundException::new);
        recipeRepository.delete(recipe);
        return mapper.map(recipe, RecipeDTO.class);
    }

}

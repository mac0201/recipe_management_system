package recipes.business_layer;

import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;
import recipes.persistence_layer.RecipeRepository;

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
        Recipe added = this.recipeRepository.addRecipe(mapper.map(recipeDTO, Recipe.class));
        return new AddResponseDTO(added.getId());
    }

    public List<RecipeDTO> getRecipes() {
        return recipeRepository.getAllRecipes()
                .stream()
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    public RecipeDTO getRecipeById(int id) {
        return mapper.map(recipeRepository.getRecipe(id - 1), RecipeDTO.class);
    }

}

package recipes.business_layer;

import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;

@Service
public class RecipeService {

    private final Mapper mapper;

    private Recipe storedRecipe;

    public RecipeService(Mapper modelMapper) {
        this.mapper = modelMapper;
    }

    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        this.storedRecipe = mapper.map(recipeDTO, Recipe.class);
        return recipeDTO;
    }

    public RecipeDTO getRecipe() {
        if (storedRecipe == null) throw new CustomExceptions.RecipeNotFoundException();
        return mapper.map(storedRecipe, RecipeDTO.class);
    }


}

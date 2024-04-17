package recipes.persistence_layer;

import org.springframework.stereotype.Repository;
import recipes.business_layer.domain.Recipe;
import recipes.exceptions.CustomExceptions;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeRepository {

    private final List<Recipe> recipes = new ArrayList<>();
    private int ID = 1;

    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(ID);
        recipes.add(recipe);
        ID++;
        return recipe;
    }

    public List<Recipe> getAllRecipes() {
        if (recipes.isEmpty()) throw new CustomExceptions.RecipeRepositoryEmpty();
        return recipes;
    }

    public Recipe getRecipe(int id) {
        if (recipes.isEmpty() || id >= recipes.size()) throw new CustomExceptions.RecipeNotFoundException();
        return recipes.get(id);
    }

}

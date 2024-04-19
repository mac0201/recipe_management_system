package recipes.persistence_layer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import recipes.business_layer.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Streamable<Recipe> findAll();
}

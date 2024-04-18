package recipes.persistence_layer;

import org.springframework.data.repository.CrudRepository;
import recipes.business_layer.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

package recipes.persistence_layer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.domain.User;

import java.util.stream.Stream;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Streamable<Recipe> findAll();
    Stream<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
    Stream<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    Stream<Recipe> findAllByUser(User user);
    boolean existsById(Long id);
}

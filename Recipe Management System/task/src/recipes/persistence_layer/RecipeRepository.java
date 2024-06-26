package recipes.persistence_layer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import recipes.business_layer.domain.Recipe;

import java.util.stream.Stream;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Streamable<Recipe> findAll();
    Stream<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);
    Stream<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    Stream<Recipe> findAllByUserEmail(String userEmail);
    boolean existsById(Long id);
}

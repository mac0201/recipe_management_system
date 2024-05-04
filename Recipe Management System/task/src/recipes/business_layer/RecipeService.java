package recipes.business_layer;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.business_layer.domain.Recipe;
import recipes.business_layer.domain.User;
import recipes.business_layer.dto.AddResponseDTO;
import recipes.business_layer.dto.RecipeDTO;
import recipes.exceptions.CustomExceptions;
import recipes.persistence_layer.RecipeRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final AuthService authService;
    private final Mapper mapper;

    public RecipeService(RecipeRepository recipeRepository, Mapper modelMapper, AuthService authService) {
        this.recipeRepository = recipeRepository;
        this.mapper = modelMapper;
        this.authService = authService;
    }

    @Transactional
    public AddResponseDTO addRecipe(RecipeDTO recipeDTO) {

        AuthService.UserAdapter adapter = (AuthService.UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = authService.getUser(adapter.getUsername());



//        User user = adapter.getUser();

        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        recipe.setDate(LocalDateTime.now());
        recipe.setUser(user);
//        user.getRecipes().add(recipe);

        recipe = this.recipeRepository.save(recipe);


        System.out.println(recipe);

        return new AddResponseDTO(recipe.getRecipeId());
    }

    public AddResponseDTO addMultipleRecipes(List<RecipeDTO> recipeDTOList) {
        recipeDTOList.stream()
                .map(r -> mapper.map(r, Recipe.class))
                .forEach(r -> {
                    r.setDate(LocalDateTime.now());
                    this.recipeRepository.save(r);
                });
        return new AddResponseDTO(recipeDTOList.size());
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
    public void deleteRecipe(long id) {
        // Find Optional<Recipe> by id and throw exception if it does not exist, otherwise delete recipe from database,
        // and return a mapped RecipeDTO object
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(CustomExceptions.RecipeNotFoundException::new);
        recipeRepository.delete(recipe);
    }

    // Update recipe with given id
    public AddResponseDTO updateRecipe(long id, RecipeDTO recipeDTO) {
        // find existing
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(CustomExceptions.RecipeNotFoundException::new);
        // update fields
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setCategory(recipeDTO.getCategory());
        recipe.setDate(LocalDateTime.now());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setDirections(recipeDTO.getDirections());

        recipeRepository.save(recipe);
        return new AddResponseDTO(id);
    }

    @Transactional
    public List<RecipeDTO> getRecipesByName(String name) {
        return recipeRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    @Transactional
    public List<RecipeDTO> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category)
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    @Transactional
    public List<RecipeDTO> getRecipesFromCurrentUser() {
        AuthService.UserAdapter user = (AuthService.UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recipeRepository.findAllByUser(user.getUser())
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .toList();

    }

}

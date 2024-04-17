package recipes.exceptions;

public class CustomExceptions {
    public static class RecipeNotFoundException extends RuntimeException {
        public RecipeNotFoundException() { super("Recipe not found"); }
    }

    public static class RecipeRepositoryEmpty extends RuntimeException {
        public RecipeRepositoryEmpty() { super("Recipe repository is empty"); }
    }
}

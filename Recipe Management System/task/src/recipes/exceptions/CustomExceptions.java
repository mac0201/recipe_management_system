package recipes.exceptions;

public class CustomExceptions {
    public static class RecipeNotFoundException extends RuntimeException {
        public RecipeNotFoundException() { super("Recipe not found"); }
    }

    public static class RecipeRepositoryEmpty extends RuntimeException {
        public RecipeRepositoryEmpty() { super("Recipe repository is empty"); }
    }

    public static class InvalidSearchParameterException extends RuntimeException {
        public InvalidSearchParameterException() { super("Invalid search parameter(s)"); }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException() { super("Email already exists"); }
    }
}

package recipes.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import recipes.exceptions.CustomExceptions.*;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class ControllerExceptionHandlers extends ResponseEntityExceptionHandler {

    // handler for empty repository
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(RecipeRepositoryEmpty.class)
    public ResponseEntity<?> handleRecipeRepositoryEmpty() {
        return ResponseEntity.noContent().build();
    }

    // handler for no recipes with given id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<?> handleRecipeNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.build("Recipe not found"));
    }

    // Most likely thrown when model mapping of class fails
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ClassCastException.class)
    protected ResponseEntity<Object> handleClassCastException(ClassCastException ex) {
        return ResponseEntity.internalServerError().body(
                ErrorResponse.build("Server encountered an issue while fulfilling the request"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class,
            RegistrationParametersInvalidException.class,
            InvalidSearchParameterException.class,
            UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleDataValidationExceptions(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.build(ex.getMessage()));

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.build(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.build(ex.getMessage()));
    }

    // Spring standard exceptions
    // handler for invalid request bodies
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(ErrorResponse.build("Invalid request"));
    }

    // handler for javax validation exception (parameters marked with @Valid)
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(ErrorResponse.build("Missing or invalid request arguments"));
    }

    // Represents an error response object used to communicate errors to the client.
    private record ErrorResponse(String message) {
        static ErrorResponse build(String message) {
            return new ErrorResponse(message);
        }
    }
}

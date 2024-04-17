package recipes.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import recipes.exceptions.CustomExceptions.*;


@ControllerAdvice
public class ControllerExceptionHandlers extends ResponseEntityExceptionHandler {

    // handler for no recipes
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<?> handleRecipeNotFoundException() {
        return ResponseEntity.noContent().build();
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

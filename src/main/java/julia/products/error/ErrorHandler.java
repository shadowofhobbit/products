package julia.products.error;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<?> handleEmptyResult() {
        return ResponseEntity.notFound().build();
    }
}

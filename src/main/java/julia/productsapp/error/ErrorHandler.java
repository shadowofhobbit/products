package julia.productsapp.error;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<?> handleEmptyResult() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    ErrorRepresentation handleNoSuchElement() {
        return new ErrorRepresentation(404, "No product with such id");
    }

    @ExceptionHandler(NoDataException.class)
    ErrorRepresentation handleNoData(NoDataException e) {
        return new ErrorRepresentation(400, e.getMessage());
    }
}

package julia.productsapp.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class ErrorHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<?> handleEmptyResult(EmptyResultDataAccessException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    ErrorRepresentation handleNoSuchElement(NoSuchElementException e) {
        log.error("Product not found", e);
        return new ErrorRepresentation(404, "No product with such id");
    }

    @ExceptionHandler(NoDataException.class)
    ErrorRepresentation handleNoData(NoDataException e) {
        log.error(e.getMessage(), e);
        return new ErrorRepresentation(400, e.getMessage());
    }
}

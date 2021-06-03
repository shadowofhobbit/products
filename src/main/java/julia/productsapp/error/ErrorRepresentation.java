package julia.productsapp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ErrorRepresentation {
    private final Integer errorCode;
    private final String errorMessage;
}

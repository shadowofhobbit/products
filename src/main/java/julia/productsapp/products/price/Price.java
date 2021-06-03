package julia.productsapp.products.price;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class Price {
    private String currency;
    @NotNull
    @Positive
    private BigDecimal price;

    public Price() {
    }

    public Price(String currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
    }

}

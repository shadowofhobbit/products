package julia.products.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {
    private String currency;
    private BigDecimal price;

    public Price() {
    }

    public Price(String currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
    }

}

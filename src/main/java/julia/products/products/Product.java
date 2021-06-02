package julia.products.products;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
public class Product {
    private Long id;
    @NotEmpty
    @Valid
    private List<ProductDetails> productDetails;
    @NotEmpty
    @Valid
    private List<Price> prices;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

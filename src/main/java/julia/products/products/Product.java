package julia.products.products;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

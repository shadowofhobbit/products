package julia.products.products;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private List<Price> prices;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

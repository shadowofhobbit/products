package julia.productsapp.products.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductClientDto {
    private Long id;
    private String language;
    private String title;
    private String description;
    private String currency;
    private BigDecimal price;
    private LocalDate createdAt;
    private LocalDate updatedAt;


}

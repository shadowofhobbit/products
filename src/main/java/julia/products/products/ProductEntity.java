package julia.products.products;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity(name="products")
public class ProductEntity {
    @Id
    @SequenceGenerator(name="products_id_seq",
            sequenceName="products_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}

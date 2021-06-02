package julia.products.products;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;


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

    public void setPrices(Set<PriceEntity> prices) {
        this.prices.clear();
        this.prices.addAll(prices);
    }

    @OneToMany(fetch = FetchType.LAZY, cascade=ALL, mappedBy="product", orphanRemoval = true)
    private Set<PriceEntity> prices = new HashSet<>();
    private LocalDate createdAt;
    private LocalDate updatedAt;

}

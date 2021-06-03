package julia.productsapp.products.price;

import julia.productsapp.products.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name="prices")
public class PriceEntity {
    @EmbeddedId
    private PriceEntityId id;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    private ProductEntity product;
}

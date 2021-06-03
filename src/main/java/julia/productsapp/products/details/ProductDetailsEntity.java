package julia.productsapp.products.details;

import julia.productsapp.products.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="details")
public class ProductDetailsEntity {
    @EmbeddedId
    private ProductDetailsId id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    private ProductEntity product;
}

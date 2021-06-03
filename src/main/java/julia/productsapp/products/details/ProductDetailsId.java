package julia.productsapp.products.details;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ProductDetailsId implements Serializable {
    private static final long serialVersionUID = -5073745645379676230L;
    private String language;
    private Long productId;

    public ProductDetailsId() {
    }

    public ProductDetailsId(String language, Long productId) {
        this.language = language;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDetailsId)) return false;
        ProductDetailsId that = (ProductDetailsId) o;
        return Objects.equals(language, that.language) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, productId);
    }
}

package julia.products.products;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PriceEntityId implements Serializable {
    private static final long serialVersionUID = -5073745645379676235L;
    private String currency;
    private Long productId;

    public PriceEntityId() {
    }

    public PriceEntityId(String currency, Long productId) {
        this.currency = currency;
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceEntityId)) return false;
        PriceEntityId that = (PriceEntityId) o;
        return Objects.equals(currency, that.currency) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, productId);
    }
}

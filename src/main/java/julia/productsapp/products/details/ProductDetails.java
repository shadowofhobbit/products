package julia.productsapp.products.details;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDetails {
    private String language;
    @NotEmpty
    private String title;
    private String description;


    public ProductDetails() {
    }

    public ProductDetails(String language, String title, String description) {
        this.language = language;
        this.title = title;
        this.description = description;
    }
}

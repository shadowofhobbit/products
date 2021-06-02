package julia.products.products;

import lombok.Data;

@Data
public class ProductDetails {
    private String language;
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

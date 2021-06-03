package julia.productsapp.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResult {
    private final List<ProductClientDto> content;
    private final int page;
    private final int size;
    private final long totalElements;
}

package julia.productsapp.products.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/products/")
@RequiredArgsConstructor
@Api(tags="Products Client")
public class ProductsClientController {
    private final ProductsClientService clientService;

    @GetMapping("search/")
    @ApiOperation("Search product by title or description. " +
            "Returns product data with price in specified currency and title and description in specified language")
    public SearchResult search(@RequestParam String term,
                                @RequestParam String currency,
                                @RequestParam String language,
                                @RequestParam int page,
                                @RequestParam int size) {
        return clientService.search(term, currency, language, page, size);
    }

    @GetMapping
    @ApiOperation("Get products with price in specified currency and title and description in specified language")
    public SearchResult getAll(@RequestParam String currency,
                                @RequestParam String language,
                                @RequestParam int page,
                                @RequestParam int size) {
        return clientService.getAll(currency, language, page, size);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id with price in specified currency and title and description in specified language")
    public ProductClientDto getById(@PathVariable long id,
                                    @RequestParam String currency,
                                    @RequestParam String language) {
        return clientService.get(id, currency, language);
    }
}

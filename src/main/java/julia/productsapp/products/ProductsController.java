package julia.productsapp.products;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Api(tags="Products")
public class ProductsController {
    private final ProductsService productsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create product")
    public Product create(@Valid @RequestBody Product product) {
        return productsService.create(product);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ResponseEntity<Product> get(@PathVariable long id) {
        return ResponseEntity.of(productsService.get(id));
    }

    @GetMapping
    @ApiOperation("Get all products")
    public List<Product> getAll() {
        return productsService.getAll();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update product")
    public Product update(@PathVariable long id, @Valid @RequestBody Product product) {
        return productsService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete product by id")
    public void delete(@PathVariable long id) {
        productsService.delete(id);
    }
}

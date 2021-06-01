package julia.products.products;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductsService {
    private final ProductsMapper productsMapper;
    private final ProductsRepository productsRepository;

    public Product create(Product product) {
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(null);
        var productEntity = productsMapper.toEntity(product);
        var savedEntity = productsRepository.save(productEntity);
        return productsMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public Optional<Product> get(long id) {
        return productsRepository.findById(id).map(productsMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productsRepository.findAll()
                .stream()
                .map(productsMapper::toDto)
                .collect(Collectors.toList());
    }

    public Product update(long id, Product product) {
        var entity = productsRepository.findById(id).orElseThrow();
        entity.setTitle(product.getTitle());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setUpdatedAt(LocalDate.now());
        var savedEntity = productsRepository.save(entity);
        return productsMapper.toDto(savedEntity);
    }

    public void delete(long id) {
        productsRepository.deleteById(id);
    }
}

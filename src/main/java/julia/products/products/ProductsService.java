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
    private final PricesMapper pricesMapper;
    private final PricesRepository pricesRepository;

    public Product create(Product product) {
        var productEntity = productsMapper.toEntity(product);
        productEntity.setCreatedAt(LocalDate.now());
        productEntity.setUpdatedAt(null);
        var priceEntities = product.getPrices().stream()
                .map(dto -> {
                    var priceEntity = pricesMapper.toEntity(dto);
                    priceEntity.setId(new PriceEntityId());
                    priceEntity.getId().setCurrency(dto.getCurrency());
                    priceEntity.setProduct(productEntity);
                    return priceEntity;
                })
                .collect(Collectors.toSet());
        productEntity.setPrices(priceEntities);
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
        var productEntity = productsRepository.findById(id).orElseThrow();
        productEntity.setTitle(product.getTitle());
        productEntity.setDescription(product.getDescription());
        var priceEntities = product.getPrices().stream()
                .map(dto -> {
                    var priceEntity = pricesMapper.toEntity(dto);
                    priceEntity.setId(new PriceEntityId());
                    priceEntity.getId().setCurrency(dto.getCurrency());
                    priceEntity.setProduct(productEntity);
                    priceEntity.getId().setProductId(id);
                    return priceEntity;
                })
                .collect(Collectors.toSet());
        pricesRepository.saveAll(priceEntities);
        productEntity.setPrices(priceEntities);
        productEntity.setUpdatedAt(LocalDate.now());
        var savedEntity = productsRepository.save(productEntity);
        return productsMapper.toDto(savedEntity);
    }

    public void delete(long id) {
        productsRepository.deleteById(id);
    }
}

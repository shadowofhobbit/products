package julia.productsapp.products;

import julia.productsapp.products.details.Product;
import julia.productsapp.products.details.ProductDetailsId;
import julia.productsapp.products.details.ProductDetailsMapper;
import julia.productsapp.products.details.ProductDetailsRepository;
import julia.productsapp.products.price.PriceEntityId;
import julia.productsapp.products.price.PriceMapper;
import julia.productsapp.products.price.PriceRepository;
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
    private final PriceMapper priceMapper;
    private final PriceRepository priceRepository;
    private final ProductDetailsMapper productDetailsMapper;
    private final ProductDetailsRepository productDetailsRepository;

    public Product create(Product product) {
        var productEntity = productsMapper.toEntity(product);
        productEntity.setCreatedAt(LocalDate.now());
        productEntity.setUpdatedAt(null);
        var priceEntities = product.getPrices().stream()
                .map(dto -> {
                    var priceEntity = priceMapper.toEntity(dto);
                    priceEntity.setId(new PriceEntityId());
                    priceEntity.getId().setCurrency(dto.getCurrency());
                    priceEntity.setProduct(productEntity);
                    return priceEntity;
                })
                .collect(Collectors.toSet());
        productEntity.setPrices(priceEntities);
        var productDetails = product.getProductDetails().stream()
                .map(dto -> {
                    var detailsEntity = productDetailsMapper.toEntity(dto);
                    detailsEntity.setId(new ProductDetailsId());
                    detailsEntity.getId().setLanguage(dto.getLanguage());
                    detailsEntity.setProduct(productEntity);
                    return detailsEntity;
                })
                .collect(Collectors.toSet());
        productEntity.setProductDetails(productDetails);
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
        var priceEntities = product.getPrices().stream()
                .map(dto -> {
                    var priceEntity = priceMapper.toEntity(dto);
                    priceEntity.setId(new PriceEntityId());
                    priceEntity.getId().setCurrency(dto.getCurrency());
                    priceEntity.setProduct(productEntity);
                    priceEntity.getId().setProductId(id);
                    return priceEntity;
                })
                .collect(Collectors.toSet());
        priceRepository.saveAll(priceEntities);
        productEntity.setPrices(priceEntities);
        var productDetails = product.getProductDetails().stream()
                .map(dto -> {
                    var detailsEntity = productDetailsMapper.toEntity(dto);
                    detailsEntity.setId(new ProductDetailsId());
                    detailsEntity.getId().setLanguage(dto.getLanguage());
                    detailsEntity.setProduct(productEntity);
                    detailsEntity.getId().setProductId(id);
                    return detailsEntity;
                })
                .collect(Collectors.toSet());
        productDetailsRepository.saveAll(productDetails);
        productEntity.setProductDetails(productDetails);
        productEntity.setUpdatedAt(LocalDate.now());
        var savedEntity = productsRepository.save(productEntity);
        return productsMapper.toDto(savedEntity);
    }

    public void delete(long id) {
        productsRepository.deleteById(id);
    }
}

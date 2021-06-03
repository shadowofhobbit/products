package julia.productsapp.products;

import julia.productsapp.products.details.*;
import julia.productsapp.products.price.Price;
import julia.productsapp.products.price.PriceEntity;
import julia.productsapp.products.price.PriceMapper;
import julia.productsapp.products.price.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
    @Mock
    private ProductsMapper productsMapper;
    @Mock
    private ProductsRepository productsRepository;
    @Mock
    private PriceMapper priceMapper;
    @Mock
    private PriceRepository priceRepository;
    @Mock
    private ProductDetailsMapper detailsMapper;
    @Mock
    private ProductDetailsRepository detailsRepository;
    private ProductsService productsService;

    @BeforeEach
    void setUp() {
        productsService = new ProductsService(productsMapper, productsRepository, priceMapper,
                priceRepository, detailsMapper, detailsRepository);
    }

    @Test
    void create() {
        var priceEntity = new PriceEntity();
        priceEntity.setPrice(BigDecimal.valueOf(500.0));
        var detailsEntity = new ProductDetailsEntity();
        detailsEntity.setTitle("phone");
        detailsEntity.setDescription("a phone");
        var productEntity = createEntity(null, Set.of(detailsEntity),
                Set.of(priceEntity), null);
        var product = createProductDto(null,
                List.of(new ProductDetails("en", "phone", "a phone")),
                List.of(new Price("EUR", BigDecimal.valueOf(500.0))), null);
        when(productsMapper.toEntity(any(Product.class))).thenReturn(productEntity);
        when(priceMapper.toEntity(any())).thenReturn(priceEntity);
        when(detailsMapper.toEntity(any())).thenReturn(detailsEntity);
        productsService.create(product);
        var captor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productsRepository).save(captor.capture());
        assertEquals(LocalDate.now(), captor.getValue().getCreatedAt());
    }

    @Test
    void getById() {
        var priceEntity = new PriceEntity();
        priceEntity.setPrice(BigDecimal.valueOf(500.0));
        var detailsEntity = new ProductDetailsEntity();
        detailsEntity.setTitle("phone");
        detailsEntity.setDescription("a phone");
        var productEntity = createEntity(1L, Set.of(detailsEntity),
                Set.of(priceEntity), LocalDate.now());
        when(productsRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        var expectedDto = createProductDto(1L,
                List.of(new ProductDetails("en", "phone", "a phone")),
                List.of(new Price("EUR", BigDecimal.valueOf(500.0))), LocalDate.now());
        when(productsMapper.toDto(productEntity)).thenReturn(expectedDto);
        var actualDto = productsService.get(1L);
        verify(productsRepository).findById(1L);
        verify(productsMapper).toDto(productEntity);
        assertEquals(expectedDto, actualDto.orElseThrow());
    }

    private Product createProductDto(Long id, List<ProductDetails> details, List<Price> prices, LocalDate createdAt) {
        var product = new Product();
        product.setId(id);
        product.setProductDetails(details);
        product.setPrices(prices);
        product.setCreatedAt(createdAt);
        return product;
    }

    private ProductEntity createEntity(Long id,  Set<ProductDetailsEntity> details,
                                       Set<PriceEntity> prices, LocalDate createdAt) {
        var product = new ProductEntity();
        product.setId(id);
        product.setProductDetails(details);
        product.setPrices(prices);
        product.setCreatedAt(createdAt);
        return product;
    }

    @Test
    void getByIdNotFound() {
        when(productsRepository.findById(1L)).thenReturn(Optional.empty());
        var product = productsService.get(1L);
        verify(productsRepository).findById(1L);
        assertTrue(product.isEmpty());
    }

    @Test
    void getAll() {
        var priceEntity = new PriceEntity();
        priceEntity.setPrice(BigDecimal.valueOf(500.0));
        var priceEntity2 = new PriceEntity();
        priceEntity2.setPrice(BigDecimal.valueOf(500.0));
        var detailsEntity = new ProductDetailsEntity();
        detailsEntity.setTitle("phone");
        detailsEntity.setDescription("a phone");
        var detailsEntity2 = new ProductDetailsEntity();
        detailsEntity2.setTitle("phone 2");
        detailsEntity2.setDescription("another phone");
        var entity = createEntity(1L, Set.of(detailsEntity),
                Set.of(priceEntity), LocalDate.of(2020,8,9));
        var entity2 = createEntity(2L, Set.of(detailsEntity2),
                Set.of(priceEntity2), LocalDate.of(2021,1,19));
        var product = createProductDto(1L,
                List.of(new ProductDetails("en", "phone", "a phone")),
                List.of(new Price("EUR", BigDecimal.valueOf(500.0))),
                LocalDate.of(2020,8,9));
        var product2 = createProductDto(2L,
                List.of(new ProductDetails("en", "phone 2", "another phone")),
                List.of(new Price("EUR", BigDecimal.valueOf(500.0))),
                LocalDate.of(2021,1,19));
        when(productsRepository.findAll()).thenReturn(List.of(entity, entity2));
        when(productsMapper.toDto(entity)).thenReturn(product);
        when(productsMapper.toDto(entity2)).thenReturn(product2);
        var products = productsService.getAll();
        assertEquals(List.of(product, product2), products);
    }

    @Test
    void update() {
        var oldPriceEntity = new PriceEntity();
        oldPriceEntity.setPrice(BigDecimal.valueOf(500.0));
        var oldDetailsEntity = new ProductDetailsEntity();
        oldDetailsEntity.setTitle("phone");
        oldDetailsEntity.setDescription("a phone");
        var entity = createEntity(1L, Set.of(oldDetailsEntity),
                Set.of(oldPriceEntity), LocalDate.of(2020,8,9));
        var product = createProductDto(1L,
                List.of(new ProductDetails("en", "updated phone", "updated phone description")),
                List.of(new Price("EUR", BigDecimal.valueOf(550.0))),
                LocalDate.of(2020,8,9));
        var priceEntity = new PriceEntity();
        priceEntity.setPrice(BigDecimal.valueOf(550.0));
        var detailsEntity = new ProductDetailsEntity();
        detailsEntity.setTitle("updated phone");
        detailsEntity.setDescription("updated phone description");
        when(priceMapper.toEntity(any())).thenReturn(priceEntity);
        when(detailsMapper.toEntity(any())).thenReturn(detailsEntity);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(entity));
        productsService.update(1L, product);
        var captor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productsRepository).save(captor.capture());
        var entityToSave = captor.getValue();
        assertEquals(LocalDate.now(), entityToSave.getUpdatedAt());
        var prices = entityToSave.getPrices();
        assertEquals(BigDecimal.valueOf(550.0), prices.iterator().next().getPrice());
    }

    @Test
    void delete() {
        productsService.delete(1L);
        verify(productsRepository).deleteById(1L);
    }
}

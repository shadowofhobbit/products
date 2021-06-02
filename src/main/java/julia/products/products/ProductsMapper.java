package julia.products.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product toDto(ProductEntity entity);
    @Mapping(target = "currency", source = "id.currency")
    Price toDto(PriceEntity entity);
    @Mapping(target = "language", source = "id.language")
    ProductDetails toDto(ProductDetailsEntity entity);
    ProductEntity toEntity(Product dto);
}

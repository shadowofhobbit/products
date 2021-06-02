package julia.products.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product toDto(ProductEntity entity);
    @Mapping(target = "currency", source = "id.currency")
    Price toDto(PriceEntity entity);
    ProductEntity toEntity(Product dto);
}

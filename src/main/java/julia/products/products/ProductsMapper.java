package julia.products.products;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product toDto(ProductEntity entity);
    ProductEntity toEntity(Product dto);
}

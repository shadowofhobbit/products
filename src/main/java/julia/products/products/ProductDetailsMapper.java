package julia.products.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {
    ProductDetailsEntity toEntity(ProductDetails dto);
}

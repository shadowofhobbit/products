package julia.productsapp.products.details;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {
    ProductDetailsEntity toEntity(ProductDetails dto);
}

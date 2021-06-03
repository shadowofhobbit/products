package julia.productsapp.products;

import julia.productsapp.products.client.ProductClientDto;
import julia.productsapp.products.details.ProductDetails;
import julia.productsapp.products.details.ProductDetailsEntity;
import julia.productsapp.products.price.Price;
import julia.productsapp.products.price.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Product toDto(ProductEntity entity);
    ProductClientDto toClientDto(ProductEntity entity, String currency, String language);
    @Mapping(target = "currency", source = "id.currency")
    Price toDto(PriceEntity entity);
    @Mapping(target = "language", source = "id.language")
    ProductDetails toDto(ProductDetailsEntity entity);
    ProductEntity toEntity(Product dto);
}

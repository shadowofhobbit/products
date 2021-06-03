package julia.productsapp.products.price;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mapping(target = "currency", source = "id.currency")
    Price toDto(PriceEntity entity);
    PriceEntity toEntity(Price dto);
}

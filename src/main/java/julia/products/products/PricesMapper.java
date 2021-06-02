package julia.products.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PricesMapper {
    @Mapping(target = "currency", source = "id.currency")
    Price toDto(PriceEntity entity);
    PriceEntity toEntity(Price dto);
}

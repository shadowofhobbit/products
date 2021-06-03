package julia.productsapp.products;

import julia.productsapp.error.NoDataException;
import julia.productsapp.products.details.ProductDetailsEntity;
import julia.productsapp.products.details.ProductDetailsId;
import julia.productsapp.products.details.ProductDetailsRepository;
import julia.productsapp.products.price.PriceEntity;
import julia.productsapp.products.price.PriceEntityId;
import julia.productsapp.products.price.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductsClientService {
    private final ProductsMapper productsMapper;
    private final ProductsRepository productsRepository;
    private final PriceRepository priceRepository;
    private final ProductDetailsRepository detailsRepository;

    public ProductClientDto get(long id, String currency, String language) {
        var product = productsRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        var dto = productsMapper.toClientDto(product, currency, language);
        setPrice(currency, product, dto);
        setTitleAndDescription(language, product, dto);
        return dto;
    }


    private void setPrice(String currency, ProductEntity product, ProductClientDto dto) {
        boolean found = false;
        for (PriceEntity priceEntity : product.getPrices()) {
            if (priceEntity.getId().getCurrency().equals(currency)) {
                dto.setPrice(priceEntity.getPrice());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoDataException("No price in specified currency");
        }
    }

    private void setTitleAndDescription(String language, ProductEntity product, ProductClientDto dto) {
        boolean found = false;
        for (ProductDetailsEntity details: product.getProductDetails()) {
            if (details.getId().getLanguage().equals(language)) {
                dto.setTitle(details.getTitle());
                dto.setDescription(details.getDescription());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoDataException("No data in specified language");
        }
    }


    public SearchResult getAll(String currency, String language, int pageNumber, int size) {
        var pageRequest = PageRequest.of(pageNumber, size);
        var page =  productsRepository
                .findAllByPricesIdCurrencyAndProductDetailsIdLanguage(currency, language, pageRequest);
        var pricesIds = page.map(product -> new PriceEntityId(currency, product.getId()));
        var prices = priceRepository.findAllById(pricesIds)
                .stream()
                .collect(Collectors.toMap(price -> price.getId().getProductId(), price -> price));
        var detailsIds = page.map(product -> new ProductDetailsId(language, product.getId()));
        var details = detailsRepository.findAllById(detailsIds)
                .stream()
                .collect(Collectors.toMap(det -> det.getId().getProductId(), det -> det));
        var result = page
                .map(productEntity -> {
                    var dto = productsMapper.toClientDto(productEntity, currency, language);
                    dto.setPrice(prices.get(dto.getId()).getPrice());
                    var detailsEntity = details.get(dto.getId());
                    dto.setTitle(detailsEntity.getTitle());
                    dto.setDescription(detailsEntity.getDescription());
                    return dto;
                });
        return new SearchResult(result.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }


}

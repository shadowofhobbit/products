package julia.productsapp.products.client;

import julia.productsapp.error.NoDataException;
import julia.productsapp.products.ProductEntity;
import julia.productsapp.products.ProductsMapper;
import julia.productsapp.products.ProductsRepository;
import julia.productsapp.products.details.ProductDetailsEntity;
import julia.productsapp.products.price.PriceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductsClientService {
    private final ProductsMapper productsMapper;
    private final ProductsRepository productsRepository;

    public ProductClientDto get(long id, String currency, String language) {
        var product = productsRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        var dto = productsMapper.toClientDto(product, currency, language);
        setPrice(currency, product, dto);
        setTitleAndDescription(language, product, dto);
        return dto;
    }


    private void setPrice(String currency, ProductEntity product, ProductClientDto dto) {
        var price = product.getPrices()
                .stream()
                .filter(priceEntity -> priceEntity.getId().getCurrency().equals(currency))
                .map(PriceEntity::getPrice)
                .findFirst()
                .orElseThrow(() -> new NoDataException("No price in specified currency"));
        dto.setPrice(price);
    }

    private void setTitleAndDescription(String language, ProductEntity product, ProductClientDto dto) {
        var details = product.getProductDetails()
                .stream()
                .filter(d -> d.getId().getLanguage().equals(language))
                .findFirst()
                .orElseThrow(() -> new NoDataException("No data in specified language"));
        dto.setTitle(details.getTitle());
        dto.setDescription(details.getDescription());
    }


    public SearchResult getAll(String currency, String language, int pageNumber, int size) {
        var pageRequest = PageRequest.of(pageNumber, size);
        var page =  productsRepository
                .findAllByCurrencyAndLanguage(currency, language, pageRequest);
        return new SearchResult(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }


    public SearchResult search(String term, String currency, String language, int pageNumber, int size) {
        var pageRequest = PageRequest.of(pageNumber, size);
        var page = productsRepository
                .findByTitleOrDescription(term, currency, language, pageRequest);
        return new SearchResult(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements());
    }
}

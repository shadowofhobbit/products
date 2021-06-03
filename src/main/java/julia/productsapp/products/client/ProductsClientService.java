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

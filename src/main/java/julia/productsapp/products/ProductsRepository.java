package julia.productsapp.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllByPricesIdCurrencyAndProductDetailsIdLanguage(String currency, String language, Pageable pageable);
}

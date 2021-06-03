package julia.productsapp.products;

import julia.productsapp.products.client.ProductClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select new julia.productsapp.products.client.ProductClientDto(p.id, d.id.language," +
            "d.title, d.description, pr.id.currency, pr.price, p.createdAt, p.updatedAt) " +
            "from products p join p.prices pr on (p.id = pr.id.productId) " +
            "join p.productDetails d on (p.id = d.id.productId)" +
            "where (pr.id.currency = :currency) and (d.id.language = :language)")
    Page<ProductClientDto> findAllByCurrencyAndLanguage(String currency,
                                                        String language,
                                                        Pageable pageable);

    @Query("select new julia.productsapp.products.client.ProductClientDto(p.id, d.id.language," +
            "d.title, d.description, pr.id.currency, pr.price, p.createdAt, p.updatedAt) " +
            "from products p join p.prices pr on (p.id = pr.id.productId) " +
            "join p.productDetails d on (p.id = d.id.productId)" +
            "where (pr.id.currency = :currency) and (d.id.language = :language) " +
            "and (d.title like CONCAT('%',:term,'%') " +
            "or (d.description like CONCAT('%',:term,'%')) )")
    Page<ProductClientDto> findByTitleOrDescription(String term, String currency, String language, Pageable pageable);
}

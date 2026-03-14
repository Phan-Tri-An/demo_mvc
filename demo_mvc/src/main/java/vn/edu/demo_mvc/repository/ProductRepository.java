package vn.edu.demo_mvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.demo_mvc.Entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByDeletedFalse();

    Optional<Product> findByIdAndDeletedFalse(Long id);

    @Query("SELECT p FROM Product p WHERE " +
            "p.deleted = false AND " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:inStock IS NULL OR (:inStock = true AND p.quantity > 0) OR (:inStock = false AND p.quantity = 0))")
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice,
                                 @Param("inStock") Boolean inStock);

    Page<Product> findAllByDeletedFalse(Pageable pageable);
}
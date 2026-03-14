package vn.edu.demo_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_mvc.Entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    boolean existsByProductId(Long productId);

    @org.springframework.data.jpa.repository.Query("SELECT p.id as productId, p.name as productName, SUM(od.quantity) as totalSold " +
            "FROM OrderDetail od JOIN od.product p JOIN od.order o " +
            "WHERE o.status IN ('PAID', 'COMPLETED') " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(od.quantity) DESC")
    java.util.List<vn.edu.demo_mvc.DTO.TopProductDTO> getTopSelling(org.springframework.data.domain.Pageable pageable);
}

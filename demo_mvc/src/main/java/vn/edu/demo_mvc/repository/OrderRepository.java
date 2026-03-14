package vn.edu.demo_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_mvc.Entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @org.springframework.data.jpa.repository.Query("SELECT FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d') as timePeriod, SUM(o.totalAmount) as totalRevenue " +
            "FROM Order o WHERE o.status IN ('PAID', 'COMPLETED') " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderDate, '%Y-%m-%d')")
    java.util.List<vn.edu.demo_mvc.DTO.RevenueReportDTO> getDailyRevenue();
}

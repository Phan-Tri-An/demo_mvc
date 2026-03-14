package vn.edu.demo_mvc.DTO;
import java.math.BigDecimal;

public interface RevenueReportDTO {
    String getTimePeriod();
    BigDecimal getTotalRevenue();
}
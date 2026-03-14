package vn.edu.demo_mvc.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.demo_mvc.repository.OrderDetailRepository;
import vn.edu.demo_mvc.repository.OrderRepository;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final OrderRepository orderRepo;
    private final OrderDetailRepository detailRepo;

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRevenue() {
        return ResponseEntity.ok(orderRepo.getDailyRevenue());
    }

    @GetMapping("/top-products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTopProducts() {
        return ResponseEntity.ok(detailRepo.getTopSelling(org.springframework.data.domain.PageRequest.of(0, 5)));
    }
}

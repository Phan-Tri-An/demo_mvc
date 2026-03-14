package vn.edu.demo_mvc.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_mvc.DTO.OrderRequestDTO;
import vn.edu.demo_mvc.DTO.OrderResponseDTO;
import vn.edu.demo_mvc.Service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    @PostMapping
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO request){
        return service.createOrder(request);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.payOrder(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelOrder(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<OrderResponseDTO> completeOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.completeOrder(id));
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrderHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getOrdersByCustomer(customerId));
    }
}

package vn.edu.demo_mvc.Service;

import vn.edu.demo_mvc.DTO.OrderRequestDTO;
import vn.edu.demo_mvc.DTO.OrderResponseDTO;

import java.util.List;


public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO request);
    OrderResponseDTO payOrder(Long id);
    OrderResponseDTO cancelOrder(Long id);
    OrderResponseDTO completeOrder(Long id);
    List<OrderResponseDTO> getOrdersByCustomer(Long customerId);
}

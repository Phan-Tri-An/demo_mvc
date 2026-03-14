package vn.edu.demo_mvc.mapper;

import vn.edu.demo_mvc.DTO.OrderResponseDTO;
import vn.edu.demo_mvc.Entity.Order;

public class OrderMapper {
    public static OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomer() != null ? order.getCustomer().getName() : "Khách vãng lai")
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
}

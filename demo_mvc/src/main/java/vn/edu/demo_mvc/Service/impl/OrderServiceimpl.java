package vn.edu.demo_mvc.Service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.edu.demo_mvc.DTO.OrderDetailDTO;
import vn.edu.demo_mvc.DTO.OrderRequestDTO;
import vn.edu.demo_mvc.DTO.OrderResponseDTO;
import vn.edu.demo_mvc.Entity.Order;
import vn.edu.demo_mvc.Entity.OrderDetail;
import vn.edu.demo_mvc.Entity.Product;
import vn.edu.demo_mvc.Service.OrderService;
import vn.edu.demo_mvc.repository.OrderRepository;
import vn.edu.demo_mvc.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceimpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final vn.edu.demo_mvc.repository.VoucherRepository voucherRepository;
    private final vn.edu.demo_mvc.repository.CustomerRepository customerRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order order = new Order();
        vn.edu.demo_mvc.Entity.Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng có ID: " + request.getCustomerId()));
        order.setCustomer(customer);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(vn.edu.demo_mvc.enums.OrderStatus.PENDING);

        List<OrderDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        Map<Long, Integer> mergedItems = new HashMap<>();
        for (OrderDetailDTO item : request.getItem()) {
            mergedItems.put(item.getProductId(),
                    mergedItems.getOrDefault(item.getProductId(), 0) + item.getQuantity());
        }

        for (Map.Entry<Long, Integer> entry : mergedItems.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có ID: " + productId));

            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng. Chỉ còn: " + product.getQuantity());
            }

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantity);
            detail.setPrice(product.getPrice());
            detail.setSubTotal(subtotal);

            total = total.add(subtotal);

            product.setQuantity(product.getQuantity() - quantity);

            details.add(detail);
        }

        if (request.getVoucherCode() != null && !request.getVoucherCode().trim().isEmpty()) {
            vn.edu.demo_mvc.Entity.Voucher voucher = voucherRepository.findByCode(request.getVoucherCode())
                    .orElseThrow(() -> new RuntimeException("Mã Voucher không tồn tại!"));

            if (voucher.getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Voucher này đã hết hạn sử dụng!");
            }

            BigDecimal discount = BigDecimal.ZERO;
            if (voucher.getType() == vn.edu.demo_mvc.enums.DiscountType.PERCENTAGE) {
                BigDecimal percent = voucher.getDiscountValue().divide(BigDecimal.valueOf(100));
                discount = total.multiply(percent);
            } else {
                discount = voucher.getDiscountValue();
            }

            total = total.subtract(discount);

            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }
        }
        order.setTotalAmount(total);
        order.setOrderDetails(details);

        Order saved = orderRepository.save(order);

        return vn.edu.demo_mvc.mapper.OrderMapper.toDTO(saved);
    }

    @Override
    public OrderResponseDTO payOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có ID: " + id));

        if (order.getStatus() == vn.edu.demo_mvc.enums.OrderStatus.CANCELLED) {
            throw new RuntimeException("Đơn hàng đã bị hủy, không thể thanh toán!");
        }

        order.setStatus(vn.edu.demo_mvc.enums.OrderStatus.PAID);
        return vn.edu.demo_mvc.mapper.OrderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có ID: " + id));

        if (order.getStatus() == vn.edu.demo_mvc.enums.OrderStatus.PAID ||
                order.getStatus() == vn.edu.demo_mvc.enums.OrderStatus.COMPLETED) {
            throw new RuntimeException("Không thể hủy đơn hàng đã thanh toán hoặc hoàn thành!");
        }

        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            product.setQuantity(product.getQuantity() + detail.getQuantity());
            productRepository.save(product);
        }
        order.setStatus(vn.edu.demo_mvc.enums.OrderStatus.CANCELLED);
        return vn.edu.demo_mvc.mapper.OrderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO completeOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng có ID: " + id));
        if (order.getStatus() == vn.edu.demo_mvc.enums.OrderStatus.CANCELLED) {
            throw new RuntimeException("Đơn hàng đã bị hủy, không thể hoàn thành!");
        }
        order.setStatus(vn.edu.demo_mvc.enums.OrderStatus.COMPLETED);
        return vn.edu.demo_mvc.mapper.OrderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(vn.edu.demo_mvc.mapper.OrderMapper::toDTO)
                .toList();
    }
}
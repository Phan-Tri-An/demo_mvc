package vn.edu.demo_mvc.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Table(name="tblOrderDetails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}

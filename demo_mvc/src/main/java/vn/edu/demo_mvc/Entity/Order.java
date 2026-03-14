package vn.edu.demo_mvc.Entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.demo_mvc.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "tblOrders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



}

package vn.edu.demo_mvc.Entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.demo_mvc.enums.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name= "tblVouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    private BigDecimal discountValue;

    private LocalDateTime expirationDate;
}
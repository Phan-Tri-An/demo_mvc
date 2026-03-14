package vn.edu.demo_mvc.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name= "tblCustomers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

}
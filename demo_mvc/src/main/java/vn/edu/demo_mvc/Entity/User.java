package vn.edu.demo_mvc.Entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.demo_mvc.enums.Role;

@Entity
@Table(name = "tblUsers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
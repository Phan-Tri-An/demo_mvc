package vn.edu.demo_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_mvc.Entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
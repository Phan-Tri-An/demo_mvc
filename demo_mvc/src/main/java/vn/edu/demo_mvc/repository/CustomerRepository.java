package vn.edu.demo_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_mvc.Entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
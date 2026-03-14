package vn.edu.demo_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.demo_mvc.Entity.Voucher;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Optional<Voucher> findByCode(String code);
}
package WebsiteBanDienThoai.repository;

import WebsiteBanDienThoai.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {

}
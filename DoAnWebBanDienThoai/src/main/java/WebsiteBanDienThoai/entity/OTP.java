package WebsiteBanDienThoai.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="otp")
public class OTP{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="otp_email")
    private String email;
    @Column(name="otp_code")
    private String otp;

}

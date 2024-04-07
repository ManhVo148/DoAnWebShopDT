package WebsiteBanDienThoai.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Ma San Pham")
    private int productId;
    @Column(name = "Ten San Pham")
    private String name;
    @Column(name = "Gia San Pham")
    private double price;
    @Column(name = "So Luong")
    private double quantity = 1;
}

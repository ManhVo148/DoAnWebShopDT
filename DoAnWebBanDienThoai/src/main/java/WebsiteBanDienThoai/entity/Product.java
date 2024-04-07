package WebsiteBanDienThoai.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Data
@Entity
@Table(name="product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="product_name")
    private String name;
    @Column(name="product_price")
    private double price;
    @Column(name="product_quantity")
    private int quantity;

    @Column(name="product_image")
    private byte[] image;

    @Transient
    private String base64Image;  // Trường mới để lưu trữ chuỗi Base64

    // Getters và setters cho base64Image

    public String getBase64Image() {
        if (image != null && image.length > 0) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



}

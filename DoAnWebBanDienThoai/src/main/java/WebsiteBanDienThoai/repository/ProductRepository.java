package WebsiteBanDienThoai.repository;

import WebsiteBanDienThoai.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT p from Product p where p.name LIKE %?1%")
    Page<Product> search(String keyword, Pageable pageable);

}

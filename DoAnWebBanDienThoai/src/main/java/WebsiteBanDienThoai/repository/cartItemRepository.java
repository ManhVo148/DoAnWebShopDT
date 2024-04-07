package WebsiteBanDienThoai.repository;

import WebsiteBanDienThoai.entity.CartItem;
import WebsiteBanDienThoai.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cartItemRepository extends JpaRepository<CartItem,Integer> {
}

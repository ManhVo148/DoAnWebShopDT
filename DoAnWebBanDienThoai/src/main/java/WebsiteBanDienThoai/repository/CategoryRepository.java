package WebsiteBanDienThoai.repository;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT p from Category p where p.name LIKE %?1%")
    Page<Category> search(String keyword, Pageable pageable);
}

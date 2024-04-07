package WebsiteBanDienThoai.repository;

import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT p from User p where p.name LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);
}

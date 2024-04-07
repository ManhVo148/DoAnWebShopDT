package WebsiteBanDienThoai.service;

import WebsiteBanDienThoai.entity.Product;
import org.springframework.data.domain.Page;


import java.util.List;

public interface StoreService {
    List<Product> getAllProducts();
    Page<Product> findPaginatedStore(int pageNo, int pageSize);
    Page<Product> findPaginatedStore (int pageNo, int pageSize, String sortField, String sortDirection, String keyword);
}

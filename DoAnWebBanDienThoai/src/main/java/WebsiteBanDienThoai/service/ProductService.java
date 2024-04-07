package WebsiteBanDienThoai.service;

import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {



    List<Product> getAllProducts();



    void saveProduct(Product product);
    Product getProductById(int id);
    void deleteProductById(int id);

    void updateProduct(Product product);
    Page<Product> findPaginated(int pageNo, int pageSize);
    Page<Product> findPaginated (int pageNo, int pageSize, String sortField,String sortDirection, String keyword);



}
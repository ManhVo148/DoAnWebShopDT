package WebsiteBanDienThoai.service;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService  {

    public List<Category> getAllCategories();
    public Category getCategoryById(int id);
    public Category saveCategory(Category category);
    public Category addCategory(Category category);
    public void deleteCategoryById(int id);

    public void updateCategory(Category category);

    Page<Category> findPaginatedCategory (int pageNo, int pageSize, String sortField, String sortDirection, String keyword);
}

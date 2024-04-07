package WebsiteBanDienThoai.service.impl;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.repository.CategoryRepository;
import WebsiteBanDienThoai.repository.ProductRepository;
import WebsiteBanDienThoai.service.CategoryService;
import WebsiteBanDienThoai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Optional<Category> optional = categoryRepository.findById(id);
        Category category=null;
        if(optional.isPresent()){
            category=optional.get();

        }else{
            throw new RuntimeException("Product not found by id::"+id);
        }
        return category;
    }

    @Override
    public Page<Category> findPaginatedCategory(int pageNo, int pageSize, String sortField, String sortDirection, String keyword){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        if(keyword == null) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.search(keyword, pageable);
    }

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }
    @Override
    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(int id){
        this.categoryRepository.deleteById(id);
    }

    @Override
    public void updateCategory(Category category) {
        this.categoryRepository.save(category);
    }

}
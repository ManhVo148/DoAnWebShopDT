package WebsiteBanDienThoai.service.impl;

import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.repository.ProductRepository;
import WebsiteBanDienThoai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts(){


        return productRepository.findAll();
    }
    @Override
    public void saveProduct (Product product){
        this.productRepository.save(product);
    }
    @Override
    public Product getProductById(int id){
        Optional<Product> optional = productRepository.findById(id);
        Product product=null;
        if(optional.isPresent()){
            product=optional.get();

        }else{
            throw new RuntimeException("Product not found by id::"+id);
        }
        return product;

    }



    @Override
    public void deleteProductById(int id){
        this.productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1, pageSize);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField,String sortDirection, String keyword){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        if(keyword == null) {
            return productRepository.findAll(pageable);
        }
        return productRepository.search(keyword, pageable);
    }



}
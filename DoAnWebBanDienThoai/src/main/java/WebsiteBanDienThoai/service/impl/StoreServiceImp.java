package WebsiteBanDienThoai.service.impl;

import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.repository.StoreRepository;
import WebsiteBanDienThoai.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImp implements StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Override
    public List<Product> getAllProducts(){
        return storeRepository.findAll();
    }

    @Override
    public Page<Product> findPaginatedStore(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1, pageSize);
        return this.storeRepository.findAll(pageable);
    }
    @Override
    public Page<Product> findPaginatedStore(int pageNo, int pageSize, String sortField, String sortDirection, String keyword){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        if(keyword == null) {
            return storeRepository.findAll(pageable)    ;

        }
        return storeRepository.search(keyword, pageable);
    }
}

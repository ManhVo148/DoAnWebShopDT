package WebsiteBanDienThoai.service;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.entity.Role;
import WebsiteBanDienThoai.entity.User;
import WebsiteBanDienThoai.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();


    public List<Role> getAllRole();
    List<User> getAllUser();

    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);

    void updateUser(User user);

    Page<User> findPaginatedUser (int pageNo, int pageSize, String sortField, String sortDirection, String keyword);
}

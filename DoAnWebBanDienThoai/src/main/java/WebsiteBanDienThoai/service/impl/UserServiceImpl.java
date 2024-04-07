package WebsiteBanDienThoai.service.impl;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.entity.Product;
import WebsiteBanDienThoai.entity.Role;
import WebsiteBanDienThoai.dto.UserDto;
import WebsiteBanDienThoai.entity.User;
import WebsiteBanDienThoai.repository.ProductRepository;
import WebsiteBanDienThoai.repository.RoleRepository;
import WebsiteBanDienThoai.repository.UserRepository;
import WebsiteBanDienThoai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }



    @Override
    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }



    @Override
    public List<User> getAllUser(){

        return userRepository.findAll();
    }
    @Override
    public void saveUser (User user){
        this.userRepository.save(user);
    }
    @Override
    public User getUserById(long id){
        Optional<User> optional = userRepository.findById(id);
        User user=null;
        if(optional.isPresent()){
            user=optional.get();

        }else{
            throw new RuntimeException("Product not found by id::"+id);
        }
        return user;

    }
    @Override
    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.save(user);
    }


    @Override
    public Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection, String keyword){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        if(keyword == null) {
            return userRepository.findAll(pageable);
        }
        return userRepository.search(keyword, pageable);
    }

}

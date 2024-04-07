package WebsiteBanDienThoai.controller;

import WebsiteBanDienThoai.entity.*;
import WebsiteBanDienThoai.service.CategoryService;
import WebsiteBanDienThoai.service.ProductService;
import WebsiteBanDienThoai.service.StoreService;
import WebsiteBanDienThoai.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class DTController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    ServletContext context;


    @GetMapping("product/view")
    public  String viewProducts(Model model){
       // model.addAttribute("listProducts",productService.getAllProducts());
        String keyword = null;
        return findPaginated(1,"name","asc","",model);
    }



    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @Param("keyword") String keyword,
                                Model model) {
        int pageSize = 5;


        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, keyword);
        List<Product> listProduct = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("keyword",keyword);
        return "/admin/listProduct";
    }



    //product/addnew
    @GetMapping ("product/addnew")
    public String showNewProductForm (Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories",categoryService.getAllCategories());
        return "/admin/addProduct";
    }
    //product/save
    @PostMapping("product/save")
    public String saveProduct (@ModelAttribute("product") Product product,@RequestParam("file")
                                                                            MultipartFile file,Model model) {
        if(!file.isEmpty()){
            try{
                String originalFilename = file.getOriginalFilename();

                byte[] bytes = file.getBytes();
                product.setImage(originalFilename.getBytes());
                String uploadDir = "D://DoAnJavaWeb/Thư mục mới/Thư mục mới/DoAnWebBanDienThoai_16_06" +
                        "/DoAnWebBanDienThoai/src/main/resources/static/img/";
                Path path = Paths.get(uploadDir + originalFilename);
                Files.write(path, bytes);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        // save product to database
        productService.saveProduct(product);
        model.addAttribute("categories",categoryService.getAllCategories());
        return "redirect:/product/view";
    }

    @GetMapping("product/edit/{id}")
    public String editProductForm(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/editProduct";
    }

    @PostMapping("product/edit/{id}")
    public String editProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/editProduct";
        } else {
            productService.updateProduct(product);
            return "redirect:/product/view";
        }
    }


    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProductById(id);
        return "redirect:/product/view";
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("user/view")
    public  String viewUsers(Model model){
//        model.addAttribute("listProducts",productService.getAllProducts());
        return findPaginatedUser(1,"name","asc","",model);
    }



    @GetMapping("/pageUser/{pageNo}")
    public String findPaginatedUser(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                    @Param("keyword") String keyword,
                                 Model model) {
        int pageSize = 5;

        Page<User> page = userService.findPaginatedUser(pageNo, pageSize, sortField, sortDir, keyword);
        List<User> listUser = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUser", listUser);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("keyword",keyword);
        return "/admin/listUser";
    }




    //product/addnew
    @GetMapping ("user/addUser")
    public String showNewUserForm (Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/admin/addUser";
    }
    //product/save
    @PostMapping("user/save")
    public String saveUser (@ModelAttribute("user") User user) {
// save product to database
        userService. saveUser(user);
        return "redirect:/user/view";
    }


    @GetMapping("user/edit/{id}")
    public String editUserForm(@PathVariable("id") int id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRole());
        return "/admin/editUser";
    }

    @PostMapping("user/edit/{id}")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("roles", userService.getAllRole());
            return "admin/editUser";
        } else {
            userService.updateUser(user);
            return "redirect:/user/view";
        }
    }


    @GetMapping("user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUserById(id);
        return "redirect:/user/view";
    }
    //////////////////////////////////////////////////////Store

    @GetMapping("/store")
    public String store(Model model){
        String keyword = null;
        return findPaginatedStore(1,"name","asc","",model);
    }

    @GetMapping("/pages/{pageNo}")
    public String findPaginatedStore(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @Param("keyword") String keyword , Model model) {
        int pageSize = 5;

        Page<Product> page = storeService.findPaginatedStore(pageNo, pageSize, sortField, sortDir, keyword);
        List<Product> listProduct = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("keyword",keyword);
        return "/store";
    }


    @GetMapping("store/detail/{id}")

    public String storeDetail(@PathVariable("id") int id,Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/detail";
    }


    // category


    @GetMapping("category/view")
    public  String viewCategory(Model model){
        // model.addAttribute("listProducts",productService.getAllProducts());
        String keyword = null;
        return findPaginatedCategory(1,"name","asc","",model);
    }



    @GetMapping("/pageCategory/{pageNo}")
    public String findPaginatedCategory(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @Param("keyword") String keyword,
                                Model model) {
        int pageSize = 5;


        Page<Category> page = categoryService.findPaginatedCategory(pageNo, pageSize, sortField, sortDir, keyword);
        List<Category> listCategory = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        model.addAttribute("keyword",keyword);
        return "/admin/listCategory";
    }


    @GetMapping("/category/addCategory")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/addCategory";
    }

    @PostMapping("/category/save")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/category/addCategory";
        }else{
            categoryService.addCategory(category);
            return "redirect:/category/view";
        }

    }
    @GetMapping("category/editCategory/{id}")
    public String editCategoryForm(@PathVariable("id") int id, Model model){
        Category editCategory = categoryService.getCategoryById(id);
        model.addAttribute("category", editCategory);
        return "/admin/editCategory";
    }

    @PostMapping("category/editCategory/{id}")
    public String editCategory(@Valid @ModelAttribute("category") Category category,  BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("category", category);
            return "admin/editCategory";
        } else {
            categoryService.saveCategory(category);
            return "redirect:/category/view";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/category/view";
    }






}

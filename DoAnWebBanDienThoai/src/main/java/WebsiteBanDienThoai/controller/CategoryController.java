package WebsiteBanDienThoai.controller;

import WebsiteBanDienThoai.entity.Category;
import WebsiteBanDienThoai.service.CategoryService;
import WebsiteBanDienThoai.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";

    }
    @GetMapping("/add")
    public String addCategoryForm(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/category/add";
        }else{
            categoryService.addCategory(category);
            return "redirect:/categories";
        }

    }
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") int id, Model model){
        Category editCategory = categoryService.getCategoryById(id);
        model.addAttribute("category", editCategory);
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@Valid @ModelAttribute("category") Category category,  BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("category", category);
            return "category/edit";
        } else {
            categoryService.saveCategory(category);
            return "redirect:/books";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }

}

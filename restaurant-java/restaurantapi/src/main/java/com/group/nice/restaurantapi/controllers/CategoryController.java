package com.group.nice.restaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.group.nice.restaurantapi.dtos.CategoryDTO;
import com.group.nice.restaurantapi.services.category.ICategoryService;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    
    @PostMapping("/add")
    public CategoryDTO addCategory(@RequestBody CategoryDTO category) {
        return iCategoryService.addCategory(category);
    }
    @PutMapping("/update/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO category) {
        return iCategoryService.updateCategory(id, category);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteCategory(@PathVariable("id") Long id) {
        return iCategoryService.deleteCategory(id);
    }
    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories() {
        return iCategoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable("id") Long id) {
        return iCategoryService.getCategory(id);
    }
}

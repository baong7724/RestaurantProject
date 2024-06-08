package com.group.nice.restaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.nice.restaurantapi.models.Category;
import com.group.nice.restaurantapi.services.ICategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    
    @GetMapping("/")
    String home() {
        return "Welcome to the Categories API!";
    }
    @PostMapping("/add")
    public Category addCategory(@RequestBody Category category) {
        return iCategoryService.addCategory(category);
    }
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        return iCategoryService.updateCategory(id, category);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteCategory(@PathVariable("id") Long id) {
        return iCategoryService.deleteCategory(id);
    }
    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return iCategoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Long id) {
        return iCategoryService.getCategory(id);
    }
}

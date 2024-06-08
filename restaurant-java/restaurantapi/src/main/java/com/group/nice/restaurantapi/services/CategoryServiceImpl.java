package com.group.nice.restaurantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.models.Category;
import com.group.nice.restaurantapi.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        if(category != null) {
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Category updateCategory(long id, Category category) {
        if(category != null) {
            Category existingCategory = categoryRepository.findById(id).orElse(null);
            if(existingCategory != null) {
                existingCategory.setName(category.getName());
                return categoryRepository.save(existingCategory);
            }
        }
        return null;
    }

    @Override
    public boolean deleteCategory(long id) {
        if(id >= 1){
            Category existingCategory = categoryRepository.findById(id).orElse(null);
            if(existingCategory != null) {
                categoryRepository.delete(existingCategory);
                return true;
            }
        }
        return false;
    }

    @Override
    public Category getCategory(long id) {
        if(id >= 1){
            return categoryRepository.findById(id).orElse(null);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

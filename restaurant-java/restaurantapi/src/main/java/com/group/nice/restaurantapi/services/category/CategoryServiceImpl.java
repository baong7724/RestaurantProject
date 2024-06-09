package com.group.nice.restaurantapi.services.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.nice.restaurantapi.dtos.CategoryDTO;
import com.group.nice.restaurantapi.models.Category;
import com.group.nice.restaurantapi.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO addCategory(CategoryDTO category) {
        if (category == null)
            throw new IllegalAccessError("Category is null");
        Category cat = new Category();
        cat.setName(category.getName());
        cat.setColorCode(category.getColorCode());
        cat.setTag(category.getTag());
        categoryRepository.save(cat);
        category.setId(cat.getId());
        return category;
    }

    @Override
    public CategoryDTO updateCategory(long id, CategoryDTO category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
    
        if (id < 1) {
            throw new IllegalArgumentException("ID is invalid");
        }
    
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
    
        if (category.getName() != null) {
            existingCategory.setName(category.getName());
        }
        if (category.getColorCode() != null) {
            existingCategory.setColorCode(category.getColorCode());
        }
        if (category.getTag() != null) {
            existingCategory.setTag(category.getTag());
        }
    
        Category updatedCategory = categoryRepository.save(existingCategory);
    
        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setId(updatedCategory.getId());
        updatedCategoryDTO.setName(updatedCategory.getName());
        updatedCategoryDTO.setColorCode(updatedCategory.getColorCode());
        updatedCategoryDTO.setTag(updatedCategory.getTag());
    
        return updatedCategoryDTO;
    }

    @Override
    public boolean deleteCategory(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID is invalid");
        }
    
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setDeletedAt(new Date());
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }
    
    @Override
    public CategoryDTO getCategory(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID is invalid");
        }
    
        Category cat = categoryRepository.findById(id).orElse(null);
        if (cat == null) {
            throw new IllegalArgumentException("Category does not exist");
        }
    
        CategoryDTO category = new CategoryDTO();
        category.setId(cat.getId());
        category.setName(cat.getName());
        category.setColorCode(cat.getColorCode());
        category.setTag(cat.getTag());
    
        return category;
    }
    
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryDTO> categories = new ArrayList<>();

        for (Category cat : list) {
            CategoryDTO category = new CategoryDTO();
            category.setId(cat.getId());
            category.setName(cat.getName());
            category.setColorCode(cat.getColorCode());
            category.setTag(cat.getTag());
            categories.add(category);
        }
        return categories;
    }
}

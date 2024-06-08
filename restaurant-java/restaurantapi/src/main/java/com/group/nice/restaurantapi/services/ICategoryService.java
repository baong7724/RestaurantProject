package com.group.nice.restaurantapi.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.group.nice.restaurantapi.dtos.CategoryDTO;

public interface ICategoryService {
    public CategoryDTO addCategory(CategoryDTO category);
    public CategoryDTO updateCategory(long id, CategoryDTO category);
    public boolean deleteCategory(long id);
    public CategoryDTO getCategory(long id);
    public List<CategoryDTO> getAllCategories();
}

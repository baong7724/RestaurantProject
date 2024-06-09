package com.group.nice.restaurantapi.services.category;

import java.util.List;


import com.group.nice.restaurantapi.dtos.CategoryDTO;

public interface ICategoryService {
    public CategoryDTO addCategory(CategoryDTO category);
    public CategoryDTO updateCategory(long id, CategoryDTO category);
    public boolean deleteCategory(long id);
    public CategoryDTO getCategory(long id);
    public List<CategoryDTO> getAllCategories();
}

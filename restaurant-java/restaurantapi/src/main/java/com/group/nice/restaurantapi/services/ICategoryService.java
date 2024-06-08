package com.group.nice.restaurantapi.services;

import java.util.List;

import com.group.nice.restaurantapi.models.Category;

public interface ICategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(long id, Category category);
    public boolean deleteCategory(long id);
    public Category getCategory(long id);
    public List<Category> getAllCategories();
}

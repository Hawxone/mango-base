package com.weekend.mango.services;

import com.weekend.mango.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();

    Category saveCategory(Category categoryModel) throws Exception;

    Category updateCategory(Long id, Category categoryModel) throws Exception;

    boolean deleteCategory(Long id) throws Exception;
}

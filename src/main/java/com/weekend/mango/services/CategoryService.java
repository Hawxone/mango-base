package com.weekend.mango.services;

import com.weekend.mango.models.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> getCategories();

    Category saveCategory(Category categoryModel) throws Exception;

    Category updateCategory(Long id, Category categoryModel) throws Exception;

    boolean deleteCategory(Long id) throws Exception;

    Map<String, Object> getPaginatedCategories(int page, int size);
}

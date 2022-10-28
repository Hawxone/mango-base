package com.weekend.mango.controllers;


import com.weekend.mango.models.Category;
import com.weekend.mango.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){

        List<Category> categories = categoryService.getCategories();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Category categoryModel = Category.builder()
                .name(name)
                .build();

        categoryModel = categoryService.saveCategory(categoryModel);

        return ResponseEntity.ok(categoryModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Category categoryModel = Category.builder()
                .name(name)
                .build();

        categoryModel = categoryService.updateCategory(id,categoryModel);

        return ResponseEntity.ok(categoryModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteCategory(@PathVariable Long id)throws Exception{

        boolean deleted;
        deleted = categoryService.deleteCategory(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }
}

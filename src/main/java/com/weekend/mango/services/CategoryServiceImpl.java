package com.weekend.mango.services;

import com.weekend.mango.entities.CategoryEntity;
import com.weekend.mango.models.Category;
import com.weekend.mango.repositories.CategoryEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryEntityRepository categoryEntityRepository;

    public CategoryServiceImpl(CategoryEntityRepository categoryEntityRepository) {
        this.categoryEntityRepository = categoryEntityRepository;
    }

    @Override
    public List<Category> getCategories() {

        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();

        return categoryEntities.stream().map((categoryEntity) ->
                Category.builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .build()
        ).toList();
    }

    @Override
    public Category saveCategory(Category categoryModel) throws Exception {

        CategoryEntity category = new CategoryEntity();

        try {
            category.setName(categoryModel.getName());
            categoryEntityRepository.save(category);

            categoryModel.setId(category.getId());
        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return categoryModel;
    }

    @Override
    public Category updateCategory(Long id, Category categoryModel) throws Exception {

        try{
            Optional<CategoryEntity> fetchCategory = categoryEntityRepository.findById(id);

            fetchCategory.ifPresent(CategoryEntity->{
                        CategoryEntity.setName(categoryModel.getName());
                        categoryEntityRepository.save(CategoryEntity);

                        categoryModel.setName(CategoryEntity.getName());
                        categoryModel.setId(CategoryEntity.getId());

                    });

        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

        return categoryModel;
    }

    @Override
    public boolean deleteCategory(Long id) throws Exception{

        try {
            Optional<CategoryEntity> fetchCategory = categoryEntityRepository.findById(id);

            fetchCategory.ifPresent(categoryEntityRepository::delete);

        }catch (Exception e){
            throw new Exception("can't delete entry "+e);
        }

        return true;
    }
}

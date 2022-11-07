package com.weekend.mango.services;

import com.weekend.mango.entities.CategoryEntity;
import com.weekend.mango.models.Category;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.repositories.CategoryEntityRepository;
import com.weekend.mango.repositories.MangaEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryEntityRepository categoryEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public CategoryServiceImpl(CategoryEntityRepository categoryEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.categoryEntityRepository = categoryEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<CategoryEntity> iterator, Long id) {

        long index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
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

    @Override
    public Map<String, Object> getPaginatedCategories(int page, int size) {

        List<Category> categoryList = new ArrayList<>();
        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        Page<CategoryEntity> categoryEntities = categoryEntityRepository.findAll(paging);
        Iterable<CategoryEntity> categoryEntityIterable = categoryEntityRepository.findAll(paging.getSort());

        List<PageIndex> pageIndices = new ArrayList<>();

        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ )
        {
            CategoryEntity categoryAlphabet = categoryEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (categoryAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(categoryEntityIterable.iterator(),categoryAlphabet.getId()));
                pageIndices.add(pageIndex);
            }
        }

        for (CategoryEntity c:categoryEntities
             ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByTagId(c.getId());
            Category category = new Category();
            category.setId(c.getId());
            category.setName(c.getName());
            category.setMangaCount(mangaCount);
            categoryList.add(category);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("categoryList",categoryList);
        response.put("currentPage",categoryEntities.getNumber());
        response.put("totalItems",categoryEntities.getTotalElements());
        response.put("totalPages",categoryEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
    }
}

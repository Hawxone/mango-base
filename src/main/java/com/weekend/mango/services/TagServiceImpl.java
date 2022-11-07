package com.weekend.mango.services;

import com.weekend.mango.entities.TagEntity;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.models.Tag;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.TagEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImpl implements TagService{

    private final TagEntityRepository tagEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public TagServiceImpl(TagEntityRepository tagEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.tagEntityRepository = tagEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<TagEntity> iterator, Long id) {

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
    public List<Tag> getTags() {

        List<TagEntity> tags = tagEntityRepository.findAll();

        return tags.stream().map((tagEntity) ->
                Tag.builder()
                        .id(tagEntity.getId())
                        .name(tagEntity.getName())
                        .build()
        ).toList();
    }


    @Override
    public Map<String, Object> getPaginatedTags(int page, int size) {

        List<Tag>tagList=new ArrayList<>();

        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());

        Page<TagEntity> tagEntities = tagEntityRepository.findAll(paging);
        Iterable<TagEntity> tagEntitiesIterator = tagEntityRepository.findAll(paging.getSort());


        List<PageIndex> pageIndices = new ArrayList<>();

        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ )
        {
            TagEntity tagAlphabet = tagEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (tagAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(tagEntitiesIterator.iterator(),tagAlphabet.getId()));
                pageIndices.add(pageIndex);
            }

        }

        for (TagEntity a:tagEntities
        ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByTagId(a.getId());
            Tag tag = new Tag();
            tag.setId(a.getId());
            tag.setName(a.getName());
            tag.setMangaCount(mangaCount);
            tagList.add(tag);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("tagList",tagList);
        response.put("currentPage",tagEntities.getNumber());
        response.put("totalItems",tagEntities.getTotalElements());
        response.put("totalPages",tagEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
    }

    @Override
    public Tag saveTag(Tag tagModel) throws Exception {

        TagEntity tag = new TagEntity();

        try {
            tag.setName(tagModel.getName());
            tagEntityRepository.save(tag);
            tagModel.setId(tag.getId());
        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return tagModel;
    }

    @Override
    public Tag updateTag(Long id, Tag tagModel) throws Exception {

        try {
            Optional<TagEntity> fetchTag = tagEntityRepository.findById(id);
            fetchTag.ifPresent(tagEntity -> {

                tagEntity.setName(tagModel.getName());
                tagEntityRepository.save(tagEntity);

                tagModel.setName(tagEntity.getName());

            });

        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

        return tagModel;
    }

    @Override
    public boolean deleteTag(Long id) throws Exception {

        try {
            Optional<TagEntity> fetchTag = tagEntityRepository.findById(id);
            fetchTag.ifPresent(tagEntityRepository::delete);
        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }
        return true;
    }

}

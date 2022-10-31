package com.weekend.mango.services;

import com.weekend.mango.entities.TagEntity;
import com.weekend.mango.models.Tag;
import com.weekend.mango.repositories.TagEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TagServiceImpl implements TagService{

    private final TagEntityRepository tagEntityRepository;

    public TagServiceImpl(TagEntityRepository tagEntityRepository) {
        this.tagEntityRepository = tagEntityRepository;
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

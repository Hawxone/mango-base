package com.weekend.mango.services;

import com.weekend.mango.entities.TagEntity;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.entities.UserTagEntity;
import com.weekend.mango.models.UserTag;
import com.weekend.mango.repositories.TagEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import com.weekend.mango.repositories.UserTagEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserTagServiceImpl implements UserTagService{

    private final UserTagEntityRepository userTagEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final TagEntityRepository tagEntityRepository;


    public UserTagServiceImpl(UserTagEntityRepository userTagEntityRepository, UserEntityRepository userEntityRepository, TagEntityRepository tagEntityRepository) {
        this.userTagEntityRepository = userTagEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.tagEntityRepository = tagEntityRepository;
    }

    public Optional<UserEntity> getUser(Long id){
        return userEntityRepository.findById(id);
    }

    public Optional<TagEntity> getTag(Long id){
        return tagEntityRepository.findById(id);
    }

    @Override
    public List<UserTag> getLikedTags(Long userId) throws Exception {

        List<UserTag> userTags = new ArrayList<>();
        try {
            List<UserTagEntity> userTagEntities = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsTrue(userId);

            for (UserTagEntity a:userTagEntities
                 ) {
                UserTag userTag = new UserTag();
                userTag.setId(a.getId());
                userTag.setUserId(a.getUser().getId());
                userTag.setTagId(a.getTag().getId());
                userTag.setTagName(a.getTag().getName());
                userTag.setIsFavorite(a.getIsLike());
                userTags.add(userTag);
            }
            return userTags;
        }catch (Exception e){
            throw new Exception("can't find entry " + e);
        }

    }

    @Override
    public List<UserTag> getDislikedTags(Long userId) throws Exception {
        List<UserTag> userTags = new ArrayList<>();
        try {
            List<UserTagEntity> userTagEntities = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsFalse(userId);
            for (UserTagEntity a:userTagEntities
            ) {
                UserTag userTag = new UserTag();
                userTag.setId(a.getId());
                userTag.setUserId(a.getUser().getId());
                userTag.setTagId(a.getTag().getId());
                userTag.setTagName(a.getTag().getName());
                userTag.setIsFavorite(a.getIsLike());
                userTags.add(userTag);
            }
            return userTags;
        }catch (Exception e){
            throw new Exception("can't find entry " + e);
        }
    }

    @Override
    public UserTag createUserTag(UserTag userTagModel) throws Exception {
        try {
            UserTagEntity userTagEntity = new UserTagEntity();
            if (getUser(userTagModel.getUserId()).isPresent()){
                userTagEntity.setUser(getUser(userTagModel.getUserId()).get());
            }else{
                throw new Exception("no user found!");
            }
            if (getTag(userTagModel.getTagId()).isPresent()){
                userTagEntity.setTag(getTag(userTagModel.getTagId()).get());
            }else{
                throw new Exception("no tag found!");
            }
            userTagEntity.setIsLike(userTagModel.getIsFavorite());
            userTagEntityRepository.save(userTagEntity);

            userTagModel.setId(userTagEntity.getId());
            return userTagModel;
        }catch (Exception e){
            throw new Exception("can't save entry" + e);
        }
    }

    @Override
    public boolean deleteUserTag(Long userId,Long tagId) throws Exception {

        Optional<UserTagEntity> fetchUserTag = userTagEntityRepository.findUserTagEntityByUser_IdAndTag_Id(userId,tagId);
        if (fetchUserTag.isPresent()){
            userTagEntityRepository.delete(fetchUserTag.get());
        }else{
            throw new Exception("tag not found!");
        }
        return true;
    }
}

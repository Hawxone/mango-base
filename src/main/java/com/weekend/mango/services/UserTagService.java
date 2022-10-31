package com.weekend.mango.services;

import com.weekend.mango.models.UserTag;

import java.util.List;

public interface UserTagService {
    List<UserTag> getLikedTags(Long userId) throws Exception;

    List<UserTag> getDislikedTags(Long userId) throws Exception;

    UserTag createUserTag(UserTag userTagModel) throws Exception;

    boolean deleteUserTag(Long userId,Long tagId) throws Exception;
}

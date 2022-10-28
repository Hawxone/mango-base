package com.weekend.mango.services;

import com.weekend.mango.models.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();

    Tag saveTag(Tag tagModel) throws Exception;

    Tag updateTag(Long id, Tag tagModel) throws Exception;

    boolean deleteTag(Long id) throws Exception;
}

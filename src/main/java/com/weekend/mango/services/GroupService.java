package com.weekend.mango.services;

import com.weekend.mango.models.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {
    List<Group> getGroups();

    Group saveGroup(Group groupModel) throws Exception;

    Group updateGroup(Long id, Group groupModel) throws Exception;

    boolean deleteGroup(Long id) throws Exception;

    Map<String, Object> getPaginatedGroups(int page, int size);
}

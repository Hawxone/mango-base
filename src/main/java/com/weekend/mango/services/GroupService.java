package com.weekend.mango.services;

import com.weekend.mango.models.Group;

import java.util.List;

public interface GroupService {
    List<Group> getGroups();

    Group saveGroup(Group groupModel) throws Exception;

    Group updateGroup(Long id, Group groupModel) throws Exception;

    boolean deleteGroup(Long id) throws Exception;
}

package com.weekend.mango.services;

import com.weekend.mango.entities.GroupEntity;
import com.weekend.mango.models.Group;
import com.weekend.mango.repositories.GroupEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{

    private GroupEntityRepository groupEntityRepository;

    public GroupServiceImpl(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    @Override
    public List<Group> getGroups() {

        List<GroupEntity> groupEntities = groupEntityRepository.findAll();

        return groupEntities.stream().map((groupEntity) ->
                Group.builder()
                        .id(groupEntity.getId())
                        .name(groupEntity.getName())
                        .build()
        ).toList();
    }

    @Override
    public Group saveGroup(Group groupModel) throws Exception {

        GroupEntity group = new GroupEntity();

        try {
            group.setName(groupModel.getName());
            groupEntityRepository.save(group);
            groupModel.setId(group.getId());

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return groupModel;
    }

    @Override
    public Group updateGroup(Long id, Group groupModel) throws Exception {

        try {
            Optional<GroupEntity> fetchGroup = groupEntityRepository.findById(id);

            fetchGroup.ifPresent(groupEntity -> {
                groupEntity.setName(groupModel.getName());
                groupEntityRepository.save(groupEntity);
                groupModel.setName(groupEntity.getName());
            });

        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }


        return groupModel;
    }

    @Override
    public boolean deleteGroup(Long id) throws Exception {

        try {
            Optional<GroupEntity> fetchGroup = groupEntityRepository.findById(id);

            fetchGroup.ifPresent(groupEntity -> {
                groupEntityRepository.delete(groupEntity);
            });

        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }

        return true;
    }
}

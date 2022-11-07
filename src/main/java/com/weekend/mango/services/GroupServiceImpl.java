package com.weekend.mango.services;

import com.weekend.mango.entities.GroupEntity;
import com.weekend.mango.models.Group;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.repositories.GroupEntityRepository;
import com.weekend.mango.repositories.MangaEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GroupServiceImpl implements GroupService{

    private final GroupEntityRepository groupEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public GroupServiceImpl(GroupEntityRepository groupEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<GroupEntity> iterator, Long id) {

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
    public Map<String, Object> getPaginatedGroups(int page, int size) {

        List<Group> groupList = new ArrayList<>();
        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        Page<GroupEntity> groupEntities = groupEntityRepository.findAll(paging);
        Iterable<GroupEntity> groupEntityIterable = groupEntityRepository.findAll(paging.getSort());

        List<PageIndex> pageIndices = new ArrayList<>();
        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ )
        {
            GroupEntity groupAlphabet = groupEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (groupAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(groupEntityIterable.iterator(),groupAlphabet.getId()));
                pageIndices.add(pageIndex);
            }
        }

        for (GroupEntity g:groupEntities
             ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByGroupId(g.getId());
            Group group = new Group();
            group.setId(g.getId());
            group.setName(g.getName());
            group.setMangaCount(mangaCount);
            groupList.add(group);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("groupList",groupList);
        response.put("currentPage",groupEntities.getNumber());
        response.put("totalItems",groupEntities.getTotalElements());
        response.put("totalPages",groupEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
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

            fetchGroup.ifPresent(groupEntityRepository::delete);

        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }

        return true;
    }


}

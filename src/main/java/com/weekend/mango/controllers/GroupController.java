package com.weekend.mango.controllers;

import com.weekend.mango.models.Group;
import com.weekend.mango.services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getGroups(){

        List<Group> groups = groupService.getGroups();

        return ResponseEntity.ok(groups);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Map<String,Object>> getPaginatedGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "24") int size){
        Map<String,Object> groupList = groupService.getPaginatedGroups(page,size);
        return ResponseEntity.ok(groupList);
    }

    @PostMapping
    public ResponseEntity<Group> saveGroup(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Group groupModel = Group.builder()
                .name(name)
                .build();

        groupModel = groupService.saveGroup(groupModel);

        return ResponseEntity.ok(groupModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id,@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Group groupModel = Group.builder()
                .name(name)
                .build();

        groupModel = groupService.updateGroup(id,groupModel);

        return ResponseEntity.ok(groupModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteGroup(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted = groupService.deleteGroup(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }


}

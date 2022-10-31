package com.weekend.mango.controllers;


import com.weekend.mango.models.UserTag;
import com.weekend.mango.services.UserTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/usertag")
public class UserTagController {

    private final UserTagService userTagService;

    public UserTagController(UserTagService userTagService) {
        this.userTagService = userTagService;
    }

    @GetMapping("/liked/user/{userId}")
    public ResponseEntity<List<UserTag>> getLikedTagsByUser(@PathVariable Long userId) throws Exception {

        List<UserTag> userTags = userTagService.getLikedTags(userId);

        return ResponseEntity.ok(userTags);
    }

    @GetMapping("/disliked/user/{userId}")
    public ResponseEntity<List<UserTag>> getDislikedTagsByUser(@PathVariable Long userId) throws Exception {

        List<UserTag> userTags = userTagService.getDislikedTags(userId);

        return ResponseEntity.ok(userTags);
    }

    @PostMapping
    public ResponseEntity<UserTag> createUserTag(@RequestParam Map<String,String> request) throws Exception {

        String userId = request.get("userId");
        String tagId = request.get("tagId");
        String isFavorite = request.get("isFavorite");

        UserTag userTagModel = new UserTag();
        userTagModel.setUserId(Long.parseLong(userId));
        userTagModel.setTagId(Long.parseLong(tagId));
        userTagModel.setIsFavorite(Boolean.parseBoolean(isFavorite));

        userTagModel = userTagService.createUserTag(userTagModel);

        return ResponseEntity.ok(userTagModel);
    }

    @DeleteMapping("/user/{userId}/tag/{tagId}")
    public ResponseEntity<Map<String,Boolean>> deleteUserTag(@PathVariable Long tagId, @PathVariable Long userId)throws Exception{

        boolean deleted;
        deleted = userTagService.deleteUserTag(tagId,userId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }
}

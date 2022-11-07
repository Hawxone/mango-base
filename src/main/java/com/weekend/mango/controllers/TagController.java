package com.weekend.mango.controllers;


import com.weekend.mango.models.Tag;
import com.weekend.mango.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getTags(){

        List<Tag> tags = tagService.getTags();

        return ResponseEntity.ok(tags);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Map<String,Object>> getPaginatedTags(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "24") int size){

        Map<String,Object> tagList = tagService.getPaginatedTags(page,size);
        return ResponseEntity.ok(tagList);
    }

    @PostMapping
    public ResponseEntity<Tag> saveTag(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Tag tagModel = Tag.builder()
                .name(name)
                .build();

        tagModel = tagService.saveTag(tagModel);

        return ResponseEntity.ok(tagModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Tag tagModel = Tag.builder()
                .name(name)
                .build();

        tagModel = tagService.updateTag(id,tagModel);

        return ResponseEntity.ok(tagModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteTag(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted = tagService.deleteTag(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }
}

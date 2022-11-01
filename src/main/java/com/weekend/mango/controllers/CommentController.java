package com.weekend.mango.controllers;

import com.weekend.mango.entities.CommentEntity;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.models.Comment;
import com.weekend.mango.models.UserModel;
import com.weekend.mango.repositories.CommentEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import com.weekend.mango.services.CommentService;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserEntityRepository userEntityRepository;
    private final CommentEntityRepository commentEntityRepository;


    public CommentController(CommentService commentService, UserEntityRepository userEntityRepository, CommentEntityRepository commentEntityRepository) {
        this.commentService = commentService;
        this.userEntityRepository = userEntityRepository;

        this.commentEntityRepository = commentEntityRepository;
    }

    @GetMapping("/manga/{mangaId}")
    public ResponseEntity<List<Comment>> getCommentsByMangaId(@PathVariable Long mangaId) throws Exception {

        List<Comment> commentList = commentService.getCommentsByMangaId(mangaId);

        return ResponseEntity.ok(commentList);
    }
    @PostMapping("/manga/{mangaId}/user/{userId}/")
    public ResponseEntity<Comment> createComment(@PathVariable Long mangaId,@PathVariable Long userId, @RequestParam Map<String,String> request) throws Exception {

        String comment = request.get("comment");

        Comment commentModel = new Comment();

        commentModel.setComment(comment);
        commentModel.setMangaId(mangaId);
        commentModel.setCreatedAt(DateTime.now());
        Optional<UserEntity> fetchUser = userEntityRepository.findById(userId);
        if (fetchUser.isPresent()){
            UserModel userModel = new UserModel();
            userModel.setId(fetchUser.get().getId());
            userModel.setUsername(fetchUser.get().getUsername());
            userModel.setImageUrl(fetchUser.get().getImageUrl());
            commentModel.setUser(userModel);
        commentModel = commentService.createComment(commentModel);
        }else{
            throw new Exception("user not found!");
        }
        return ResponseEntity.ok(commentModel);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId,@RequestParam Map<String,String> request) throws Exception {
        String comment = request.get("comment");

        Comment commentModel = new Comment();
        Optional<CommentEntity> fetchComment = commentEntityRepository.findById(commentId);
        if (fetchComment.isPresent()){
            commentModel.setId(fetchComment.get().getId());
            commentModel.setComment(comment);
            commentModel.setMangaId(fetchComment.get().getManga().getId());
            Optional<UserEntity> fetchUser = userEntityRepository.findById(fetchComment.get().getUser().getId());
            if (fetchUser.isPresent()){
                UserModel userModel = new UserModel();
                userModel.setId(fetchUser.get().getId());
                userModel.setUsername(fetchUser.get().getUsername());
                userModel.setImageUrl(fetchUser.get().getImageUrl());
                commentModel.setUser(userModel);
                commentModel = commentService.updateComment(commentModel);
            }else{
                throw new Exception("user not found!");
            }
            return ResponseEntity.ok(commentModel);
        }else {
            throw new Exception("comment not found!");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteComment(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted = commentService.deleteComment(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

}

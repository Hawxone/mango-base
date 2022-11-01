package com.weekend.mango.services;

import com.weekend.mango.entities.CommentEntity;
import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.models.Comment;
import com.weekend.mango.models.UserModel;
import com.weekend.mango.repositories.CommentEntityRepository;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentEntityRepository commentEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public CommentServiceImpl(CommentEntityRepository commentEntityRepository, UserEntityRepository userEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.commentEntityRepository = commentEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    @Override
    public List<Comment> getCommentsByMangaId(Long mangaId) throws Exception {

        try {
            List<Comment> commentList = new ArrayList<>();

            Optional<List<CommentEntity>> fetchComments = commentEntityRepository.getCommentEntitiesByManga_Id(mangaId);

            if (fetchComments.isPresent()){
                for (CommentEntity c:fetchComments.get()
                     ) {
                    Comment comment = new Comment();
                    comment.setComment(c.getComment());
                    comment.setCreatedAt(c.getCreatedAt());
                    comment.setId(c.getId());
                    Optional<UserEntity> fetchUser = userEntityRepository.findById(c.getUser().getId());
                    if (fetchUser.isPresent()){
                        UserModel userModel = new UserModel();
                        userModel.setId(fetchUser.get().getId());
                        userModel.setUsername(fetchUser.get().getUsername());
                        userModel.setImageUrl(fetchUser.get().getImageUrl());
                        comment.setUser(userModel);
                    }else{
                        throw new Exception("can't find user!");
                    }

                }
            }
            return commentList;
        }catch (Exception e){
            throw new Exception("can't find entry " + e);
        }


    }

    @Override
    public Comment createComment(Comment commentModel) throws Exception {
        try {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setComment(commentModel.getComment());
            commentEntity.setCreatedAt(commentModel.getCreatedAt());
            Optional<UserEntity> fetchUser = userEntityRepository.findById(commentModel.getUser().getId());
            fetchUser.ifPresent(commentEntity::setUser);
            Optional<MangaEntity> fetchManga = mangaEntityRepository.findById(commentModel.getMangaId());
            fetchManga.ifPresent(commentEntity::setManga);
            commentEntityRepository.save(commentEntity);

            commentModel.setId(commentEntity.getId());

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return commentModel;
    }

    @Override
    public Comment updateComment(Comment commentModel) throws Exception {

        try {
            Optional<CommentEntity> fetchComment = commentEntityRepository.findById(commentModel.getId());
            if (fetchComment.isPresent()){
                CommentEntity commentEntity = fetchComment.get();
                commentEntity.setComment(commentModel.getComment());
                commentEntityRepository.save(commentEntity);
            }
        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }
        return commentModel;
    }

    @Override
    public boolean deleteComment(Long id) throws Exception {
        try {
            Optional<CommentEntity> fetchComment = commentEntityRepository.findById(id);
            fetchComment.ifPresent(commentEntityRepository::delete);
        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }
        return true;
    }
}

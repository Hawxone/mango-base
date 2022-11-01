package com.weekend.mango.services;

import com.weekend.mango.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByMangaId(Long mangaId) throws Exception;

    Comment createComment(Comment commentModel) throws Exception;

    Comment updateComment(Comment commentModel) throws Exception;

    boolean deleteComment(Long id) throws Exception;
}

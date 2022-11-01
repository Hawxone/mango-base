package com.weekend.mango.repositories;

import com.weekend.mango.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity,Long> {

    Optional<List<CommentEntity>> getCommentEntitiesByManga_Id(Long mangaId);

}

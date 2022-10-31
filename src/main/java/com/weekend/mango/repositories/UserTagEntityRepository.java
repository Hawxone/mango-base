package com.weekend.mango.repositories;


import com.weekend.mango.entities.UserTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTagEntityRepository extends JpaRepository<UserTagEntity, Long> {

    List<UserTagEntity> findUserTagEntitiesByUser_IdAndIsLikeIsTrue(Long id);
    List<UserTagEntity> findUserTagEntitiesByUser_IdAndIsLikeIsFalse(Long id);
    Optional<UserTagEntity> findUserTagEntityByUser_IdAndTag_Id(Long userId, Long tagId);

}

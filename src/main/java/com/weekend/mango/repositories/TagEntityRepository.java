package com.weekend.mango.repositories;

import com.weekend.mango.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagEntityRepository extends JpaRepository<TagEntity,Long> {
}

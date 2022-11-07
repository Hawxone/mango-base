package com.weekend.mango.repositories;

import com.weekend.mango.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface TagEntityRepository extends JpaRepository<TagEntity,Long> {

    TagEntity findFirstByNameStartsWith(@NotBlank @Size(max = 120) String name);

}

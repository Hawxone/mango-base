package com.weekend.mango.repositories;

import com.weekend.mango.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity,Long> {

    GroupEntity findFirstByNameStartsWith(@NotBlank @Size(max = 120) String name);
}

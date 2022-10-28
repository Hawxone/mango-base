package com.weekend.mango.repositories;

import com.weekend.mango.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity,Long> {
}

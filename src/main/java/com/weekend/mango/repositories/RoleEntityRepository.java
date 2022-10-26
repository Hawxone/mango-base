package com.weekend.mango.repositories;

import com.weekend.mango.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity,Long> {

Optional<RoleEntity> findByName(String name);

}

package com.weekend.mango.repositories;

import com.weekend.mango.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}

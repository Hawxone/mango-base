package com.weekend.mango.repositories;

import com.weekend.mango.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEntityRepository extends JpaRepository<CharacterEntity,Long> {
}

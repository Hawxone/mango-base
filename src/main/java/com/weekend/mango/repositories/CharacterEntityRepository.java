package com.weekend.mango.repositories;

import com.weekend.mango.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface CharacterEntityRepository extends JpaRepository<CharacterEntity,Long> {

    CharacterEntity findFirstByNameStartsWith(@NotBlank @Size(max = 120) String name);
}

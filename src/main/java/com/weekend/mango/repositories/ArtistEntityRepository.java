package com.weekend.mango.repositories;

import com.weekend.mango.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface ArtistEntityRepository extends JpaRepository<ArtistEntity,Long> {

    ArtistEntity findFirstByNameStartsWith(@NotBlank @Size(max = 120) String name);

}

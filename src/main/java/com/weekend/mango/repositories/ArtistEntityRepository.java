package com.weekend.mango.repositories;

import com.weekend.mango.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistEntityRepository extends JpaRepository<ArtistEntity,Long> {
}

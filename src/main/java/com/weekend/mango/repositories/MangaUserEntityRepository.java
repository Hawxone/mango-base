package com.weekend.mango.repositories;

import com.weekend.mango.entities.MangaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaUserEntityRepository extends JpaRepository<MangaUserEntity,Long> {
}

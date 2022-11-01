package com.weekend.mango.repositories;

import com.weekend.mango.entities.MangaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaEntityRepository extends JpaRepository<MangaEntity,Long> {

    List<MangaEntity> findMangaEntitiesByMangaOrder(Integer order);
    MangaEntity findFirstByOrderByMangaOrderDesc();
}

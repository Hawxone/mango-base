package com.weekend.mango.repositories;

import com.weekend.mango.entities.MangaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MangaEntityRepository extends JpaRepository<MangaEntity,Long> {

    List<MangaEntity> findMangaEntitiesByMangaOrder(Integer order);
    Page<MangaEntity> findMangaEntitiesByCreatedBy_Id(Long id, Pageable pageable);

    Optional<List<MangaEntity>> findAllByMangaOrderNotNull(Sort mangaOrder);


}

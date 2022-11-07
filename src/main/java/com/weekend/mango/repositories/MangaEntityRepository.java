package com.weekend.mango.repositories;

import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MangaEntityRepository extends JpaRepository<MangaEntity,Long> {

    Optional<MangaEntity> findMangaEntitiesByMangaOrder(Integer order);
    Page<MangaEntity> findMangaEntitiesByCreatedBy_Id(Long id, Pageable pageable);

    Optional<List<MangaEntity>> findAllByMangaOrderNotNull(Sort mangaOrder);

    Long countMangaEntitiesByTagId(Long tag_id);
    Long countMangaEntitiesByArtistId(Long artist_id);
    Long countMangaEntitiesByCharacterId(Long character_id);
    Long countMangaEntitiesByParodyId(Long parody_id);
    Long countMangaEntitiesByGroupId(Long group_id);
    Long countMangaEntitiesByCategoryId(Long category_id);


}

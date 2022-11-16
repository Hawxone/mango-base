package com.weekend.mango.repositories;


import com.weekend.mango.entities.MangaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MangaEntityRepository extends JpaRepository<MangaEntity,Long>{

    Optional<MangaEntity> findMangaEntityByMangaOrder(Integer order);
    Page<MangaEntity> findMangaEntitiesByCreatedBy_Id(Long id, Pageable pageable);

    Optional<List<MangaEntity>> findAllByMangaOrderNotNull(Sort mangaOrder);

    @Query("select distinct m from MangaEntity m  left join m.artist a left join m.parody p left join m.tag t where " +
            "lower(m.title) like lower(concat('%', ?1,'%')) or " +
            "lower(a.name) like lower(concat('%', ?1,'%')) or " +
            "lower(p.name) like lower(concat('%', ?1,'%')) or " +
            "lower(t.name) like lower(concat('%', ?1,'%')) and " +
            "m.mangaOrder is not null")
    Page<MangaEntity> searchManga(String name, Pageable pageable);

    Page<MangaEntity> getMangaEntitiesByArtistNameIgnoreCase(String artistName,Pageable pageable);
    Page<MangaEntity> getMangaEntitiesByCategoryNameIgnoreCase(String categoryName,Pageable pageable);
    Page<MangaEntity> getMangaEntitiesByCharacterNameIgnoreCase(String characterName,Pageable pageable);
    Page<MangaEntity> getMangaEntitiesByGroupNameIgnoreCase(String groupName,Pageable pageable);
    Page<MangaEntity> getMangaEntitiesByParodyNameIgnoreCase(String parodyName,Pageable pageable);
    Page<MangaEntity> getMangaEntitiesByTagNameIgnoreCase(String tagName,Pageable pageable);

    Long countMangaEntitiesByTagId(Long tag_id);
    Long countMangaEntitiesByArtistId(Long artist_id);
    Long countMangaEntitiesByCharacterId(Long character_id);
    Long countMangaEntitiesByParodyId(Long parody_id);
    Long countMangaEntitiesByGroupId(Long group_id);
    Long countMangaEntitiesByCategoryId(Long category_id);
    Page<MangaEntity> findAllByMangaOrderNotNull(Pageable pageable);


}

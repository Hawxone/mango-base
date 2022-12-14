package com.weekend.mango.repositories;


import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PageEntityRepository extends JpaRepository<PageEntity,Long> {

    List<PageEntity> findPageEntitiesByMangaId(MangaEntity mangaId);
    Optional<PageEntity> findPageEntityByFile(String file);

    Optional<PageEntity> findPageEntityByIdAndMangaId(Long id, MangaEntity mangaId);

    Long countPageEntitiesByMangaId_Id(Long mangaId);

}

package com.weekend.mango.repositories;

import com.weekend.mango.entities.MangaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MangaUserEntityRepository extends JpaRepository<MangaUserEntity,Long> {

    Optional<MangaUserEntity> getMangaUserEntityByUserIdAndManga_Id(Long user_id, Long manga_id);

}

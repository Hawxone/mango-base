package com.weekend.mango.services;

import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.MangaUserEntity;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.models.MangaUser;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.MangaUserEntityRepository;
import com.weekend.mango.repositories.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MangaUserServiceImpl implements MangaUserService{

    private MangaUserEntityRepository mangaUserEntityRepository;
    private MangaEntityRepository mangaEntityRepository;
    private UserEntityRepository userEntityRepository;

    public MangaUserServiceImpl(MangaUserEntityRepository mangaUserEntityRepository, MangaEntityRepository mangaEntityRepository, UserEntityRepository userEntityRepository) {
        this.mangaUserEntityRepository = mangaUserEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public MangaUser getMangaUserByMangaAndUser(Long mangaId, Long userId) throws Exception {

        Optional<MangaEntity> fetchManga = mangaEntityRepository.findById(mangaId);
        if (fetchManga.isPresent()){
            Optional<UserEntity>fetchUser = userEntityRepository.findById(userId);
            if (fetchUser.isPresent()){
                Optional<MangaUserEntity> fetchMangaUser = mangaUserEntityRepository.getMangaUserEntityByUserIdAndManga_Id(userId,mangaId);
                if (fetchMangaUser.isPresent()){
                    MangaUser mangaUser = new MangaUser();
                    mangaUser.setId(fetchMangaUser.get().getId());
                    mangaUser.setIsFavorite(fetchMangaUser.get().getIsFavorite());
                    mangaUser.setCurrentPage(fetchMangaUser.get().getCurrentPage());
                    mangaUser.setIsWillRead(fetchMangaUser.get().getIsWillRead());

                    return mangaUser;
                }
            }
        }else {
            throw new Exception("manga not found!");
        }
        return null;
    }
}

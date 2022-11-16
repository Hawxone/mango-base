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

    private final MangaUserEntityRepository mangaUserEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public MangaUserServiceImpl(MangaUserEntityRepository mangaUserEntityRepository, MangaEntityRepository mangaEntityRepository, UserEntityRepository userEntityRepository) {
        this.mangaUserEntityRepository = mangaUserEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    Optional<MangaEntity> getManga(Long id){
        return mangaEntityRepository.findById(id);
    }

    Optional<UserEntity> getUser(Long id){
        return userEntityRepository.findById(id);
    }

    @Override
    public MangaUser getMangaUserByMangaAndUser(Long mangaId, Long userId) throws Exception {

        Optional<MangaEntity> fetchManga = getManga(mangaId);
        if (fetchManga.isPresent()){
            Optional<UserEntity>fetchUser = getUser(userId);
            if (fetchUser.isPresent()){
                Optional<MangaUserEntity> fetchMangaUser = mangaUserEntityRepository.getMangaUserEntityByUserIdAndManga_Id(userId,mangaId);
                if (fetchMangaUser.isPresent()){
                    MangaUser mangaUser = new MangaUser();
                    mangaUser.setId(fetchMangaUser.get().getId());
                    mangaUser.setIsFavorite(fetchMangaUser.get().getIsFavorite());
                    mangaUser.setCurrentPage(fetchMangaUser.get().getCurrentPage());
                    mangaUser.setIsWillRead(fetchMangaUser.get().getIsWillRead());
                    mangaUser.setUserId(userId);
                    mangaUser.setMangaId(mangaId);

                    return mangaUser;
                }
            }
        }else {
            throw new Exception("manga not found!");
        }
        return null;
    }

    @Override
    public MangaUser createOrUpdateMangaUser(MangaUser mangaUserModel) throws Exception {
        try {
            Optional<MangaUserEntity> fetchMangaUser = mangaUserEntityRepository.getMangaUserEntityByUserIdAndManga_Id(mangaUserModel.getUserId(), mangaUserModel.getMangaId());

            MangaUserEntity mangaUserEntity;
            if (fetchMangaUser.isPresent()){

                mangaUserEntity = fetchMangaUser.get();

                if (getManga(mangaUserModel.getMangaId()).isPresent()){
                    mangaUserEntity.setManga(getManga(mangaUserModel.getMangaId()).get());
                }else {
                    throw new Exception("Manga not found!");
                }
                if (getUser(mangaUserModel.getUserId()).isPresent()){
                    mangaUserEntity.setUser(getUser(mangaUserModel.getUserId()).get());
                }else {
                    throw new Exception("user not found!");
                }

                if (mangaUserModel.getIsFavorite()!=null){
                    mangaUserEntity.setIsFavorite(mangaUserModel.getIsFavorite());
                }
                if (mangaUserModel.getIsWillRead()!=null){
                    mangaUserEntity.setIsWillRead(mangaUserEntity.getIsWillRead());
                }
                mangaUserEntity.setCurrentPage(mangaUserModel.getCurrentPage());
                mangaUserEntityRepository.save(mangaUserEntity);
            }else {
                mangaUserEntity = new MangaUserEntity();
                    if (getManga(mangaUserModel.getMangaId()).isPresent()){
                        mangaUserEntity.setManga(getManga(mangaUserModel.getMangaId()).get());
                    }else {
                        throw new Exception("Manga not found!");
                    }
                    if (getUser(mangaUserModel.getUserId()).isPresent()){
                        mangaUserEntity.setUser(getUser(mangaUserModel.getUserId()).get());
                    }else {
                        throw new Exception("user not found!");
                    }
                    mangaUserEntity.setCurrentPage(mangaUserModel.getCurrentPage());
                    mangaUserEntity.setIsFavorite(mangaUserModel.getIsFavorite());
                    mangaUserEntity.setIsWillRead(mangaUserModel.getIsWillRead());

                    mangaUserEntityRepository.save(mangaUserEntity);
                    mangaUserModel.setId(mangaUserEntity.getId());
            }
            return mangaUserModel;
        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }
    }

    @Override
    public MangaUser updateMangaUser(MangaUser mangaUserModel) throws Exception {

        try {
            Optional<MangaUserEntity> fetchMangaUser = mangaUserEntityRepository.getMangaUserEntityByUserIdAndManga_Id(mangaUserModel.getUserId(), mangaUserModel.getMangaId());

            if (fetchMangaUser.isPresent()){
                MangaUserEntity mangaUserEntity = fetchMangaUser.get();

                if (getManga(mangaUserModel.getMangaId()).isPresent()){
                    mangaUserEntity.setManga(getManga(mangaUserModel.getMangaId()).get());
                }else {
                    throw new Exception("Manga not found!");
                }
                if (getUser(mangaUserModel.getUserId()).isPresent()){
                    mangaUserEntity.setUser(getUser(mangaUserModel.getUserId()).get());
                }else {
                    throw new Exception("user not found!");
                }

                if (mangaUserModel.getIsFavorite()!=null){
                    mangaUserEntity.setIsFavorite(mangaUserModel.getIsFavorite());
                }
                if (mangaUserModel.getIsWillRead()!=null){
                    mangaUserEntity.setIsWillRead(mangaUserEntity.getIsWillRead());
                }
                mangaUserEntity.setCurrentPage(mangaUserModel.getCurrentPage());
                mangaUserEntityRepository.save(mangaUserEntity);
                return mangaUserModel;
            }else {
                return null;
            }
        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

    }
}

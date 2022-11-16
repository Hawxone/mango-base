package com.weekend.mango.services;

import com.weekend.mango.models.MangaUser;

public interface MangaUserService {
    MangaUser getMangaUserByMangaAndUser(Long mangaId, Long userId) throws Exception;

    MangaUser createOrUpdateMangaUser(MangaUser mangaUserModel) throws Exception;

    MangaUser updateMangaUser(MangaUser mangaUserModel) throws Exception;
}

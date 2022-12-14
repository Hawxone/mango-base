package com.weekend.mango.services;

import com.weekend.mango.models.Manga;

import java.util.Map;

public interface MangaService {
    Map<String, Object> getPaginatedMangaList(int page, int size, Long userId);

    Manga createManga(Manga mangaModel) throws Exception;

    Manga getMangaById(Long id, Long user) throws Exception;

    Manga saveManga(Long id,Manga mangaModel) throws Exception;

    boolean deleteManga(Long id) throws Exception;

    Map<String, Object> getPaginatedMangaListByUser(int page, int size, Long userId);

    boolean publishManga(Long id) throws Exception;

    Manga getMangaByOrderId(Integer id, Long userId) throws Exception;

    Map<String, Object> getMangaResultList(int page, int size, String query,Long userId);

    Map<String, Object> getMangasByTagName(int page, int size, String query, String tagType, Long userId);
}

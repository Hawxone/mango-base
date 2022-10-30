package com.weekend.mango.services;

import com.weekend.mango.models.Manga;

import java.util.Map;

public interface MangaService {
    Map<String, Object> getPaginatedMangaList(int page, int size);

    Manga createManga(Manga mangaModel) throws Exception;

    Manga getMangaById(Long id) throws Exception;

    Manga saveManga(Long id,Manga mangaModel) throws Exception;
}
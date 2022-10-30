package com.weekend.mango.services;

import com.weekend.mango.models.PageModel;

import java.util.List;

public interface PageService {
    List<PageModel> getPagesByMangaId(Long mangaId) throws Exception;

    PageModel createMangaPage(Long mangaId, PageModel pageModel) throws Exception;
}

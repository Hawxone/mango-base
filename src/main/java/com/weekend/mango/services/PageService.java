package com.weekend.mango.services;

import com.weekend.mango.models.PageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PageService {
    List<PageModel> getPagesByMangaId(Long mangaId) throws Exception;

    PageModel createMangaPage(Long mangaId, PageModel pageModel, MultipartFile file) throws Exception;

    boolean deletePage(Long id, Long mangaId) throws Exception;
}

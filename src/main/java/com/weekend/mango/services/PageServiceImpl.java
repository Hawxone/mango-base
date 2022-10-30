package com.weekend.mango.services;

import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.entities.PageEntity;
import com.weekend.mango.models.PageModel;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.repositories.PageEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {

    private MangaEntityRepository mangaEntityRepository;
    private PageEntityRepository pageEntityRepository;

    public PageServiceImpl(MangaEntityRepository mangaEntityRepository, PageEntityRepository pageEntityRepository) {
        this.mangaEntityRepository = mangaEntityRepository;
        this.pageEntityRepository = pageEntityRepository;
    }

    Optional<MangaEntity> getManga(Long id){
        return mangaEntityRepository.findById(id);
    }

    Optional<PageEntity> getPage(String file){
        return pageEntityRepository.findPageEntityByFile(file);
    }

    @Override
    public List<PageModel> getPagesByMangaId(Long mangaId) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);
            List<PageModel> pages = new ArrayList<>();
            if (fetchManga.isPresent()){
                List<PageEntity> fetchMangaPages = pageEntityRepository.findPageEntitiesByMangaId(fetchManga.get());
                for (PageEntity a:fetchMangaPages
                ) {
                    PageModel pageModel = new PageModel();
                    pageModel.setId(a.getId());
                    pageModel.setPageOrder(a.getPageOrder());
                    pageModel.setFile(a.getFile());
                    pages.add(pageModel);
                }
            }
            return pages;
        }catch (Exception e){
            throw new Exception("can't execute order " + e);
        }
    }

    @Override
    public PageModel createMangaPage(Long mangaId, PageModel pageModel) throws Exception {

        Optional<PageEntity> isDuplicate = getPage(pageModel.getFile());

        if (isDuplicate.isPresent()){
            return null;
        }

        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);

            if (fetchManga.isPresent()){
                PageEntity pageEntity = new PageEntity();
                pageEntity.setPageOrder(pageModel.getPageOrder());
                pageEntity.setFile(pageModel.getFile());
                pageEntity.setMangaId(fetchManga.get());
                pageEntityRepository.save(pageEntity);
                pageModel.setId(pageEntity.getId());

                return pageModel;
            }else{
                throw new Exception("manga not found!");
            }

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }
    }

    @Override
    public boolean deletePage(Long id, Long mangaId) throws Exception {
        try {
            Optional<MangaEntity> fetchManga =getManga(mangaId);
            if (fetchManga.isPresent()){
                Optional<PageEntity> fetchPage = pageEntityRepository.findPageEntityByIdAndMangaId(id,fetchManga.get());
                fetchPage.ifPresent(pageEntity -> pageEntityRepository.delete(pageEntity));
            }else {
                throw new Exception("manga not found!");
            }
        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }
        return true;
    }
}

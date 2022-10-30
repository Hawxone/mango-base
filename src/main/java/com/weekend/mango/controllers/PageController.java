package com.weekend.mango.controllers;


import com.weekend.mango.entities.MangaEntity;
import com.weekend.mango.models.PageModel;
import com.weekend.mango.repositories.MangaEntityRepository;
import com.weekend.mango.services.PageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/manga")
public class PageController {

    private PageService pageService;
    private MangaEntityRepository mangaEntityRepository;

    public PageController(PageService pageService, MangaEntityRepository mangaEntityRepository) {
        this.pageService = pageService;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    Optional<MangaEntity> getManga(Long id){
        return mangaEntityRepository.findById(id);
    }

    @GetMapping("/{mangaId}/pages")
    public ResponseEntity<List<PageModel>> getPagesByMangaId(@PathVariable Long mangaId) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);
            if (fetchManga.isPresent()){
                List<PageModel> pages = pageService.getPagesByMangaId(mangaId);
                return ResponseEntity.ok(pages);
            }else{
                throw new Exception("manga not found");
            }
        }catch (Exception e){
            throw new Exception("can't find entry " + e);
        }
    }

    @PostMapping("/{mangaId}/pages")
    public ResponseEntity<PageModel> createMangaPage(@PathVariable Long mangaId, @RequestParam Map<String,String> request) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = getManga(mangaId);

            if (fetchManga.isPresent()){
                String file =fetchManga.get().getId()+"/"+request.get("file");
                Integer pageOrder = Integer.parseInt(request.get("pageOrder"));

                PageModel pageModel = new PageModel();
                pageModel.setFile(file);
                pageModel.setPageOrder(pageOrder);
                pageModel = pageService.createMangaPage(mangaId,pageModel);
                return ResponseEntity.ok(pageModel);
            }else {
                throw new Exception("manga not found!");
            }

        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

    }
}

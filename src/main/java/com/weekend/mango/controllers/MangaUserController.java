package com.weekend.mango.controllers;

import com.weekend.mango.models.MangaUser;
import com.weekend.mango.services.MangaUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/mangauser")
public class MangaUserController {

    private final MangaUserService mangaUserService;

    public MangaUserController(MangaUserService mangaUserService) {
        this.mangaUserService = mangaUserService;
    }

    @GetMapping("/manga/{mangaId}/user/{userId}")
    public ResponseEntity<MangaUser> getMangaUserByMangaAndUser(@PathVariable Long mangaId, @PathVariable Long userId) throws Exception {

        MangaUser mangaUser = mangaUserService.getMangaUserByMangaAndUser(mangaId,userId);

        return ResponseEntity.ok(mangaUser);
    }

    @PostMapping("/manga/{mangaId}/user/{userId}")
    public ResponseEntity<MangaUser> createMangaUser(@PathVariable Long mangaId, @PathVariable Long userId, @RequestParam Map<String,String> request) throws Exception{

        String isWillRead = request.get("isWillRead");
        String isFavorite = request.get("isFavorite");
        String currentPage = request.get("currentPage");

        MangaUser mangaUserModel = new MangaUser();
        mangaUserModel.setIsWillRead(Boolean.parseBoolean(isWillRead));
        mangaUserModel.setIsFavorite(Boolean.parseBoolean(isFavorite));
        mangaUserModel.setCurrentPage(Integer.parseInt(currentPage));
        mangaUserModel.setMangaId(mangaId);
        mangaUserModel.setUserId(userId);
        mangaUserModel = mangaUserService.createMangaUser(mangaUserModel);

        return ResponseEntity.ok(mangaUserModel);
    }

    @PutMapping("/manga/{mangaId}/user/{userId}")
    public ResponseEntity<MangaUser> updateMangaUser(@PathVariable Long mangaId, @PathVariable Long userId, @RequestParam Map<String,String> request)throws Exception{

        String isWillRead = request.get("isWillRead");
        String isFavorite = request.get("isFavorite");
        String currentPage = request.get("currentPage");

        MangaUser mangaUserModel = new MangaUser();
        mangaUserModel.setIsWillRead(Boolean.parseBoolean(isWillRead));
        mangaUserModel.setIsFavorite(Boolean.parseBoolean(isFavorite));
        mangaUserModel.setCurrentPage(Integer.parseInt(currentPage));
        mangaUserModel.setMangaId(mangaId);
        mangaUserModel.setUserId(userId);
        mangaUserModel = mangaUserService.updateMangaUser(mangaUserModel);

        return ResponseEntity.ok(mangaUserModel);
    }

}

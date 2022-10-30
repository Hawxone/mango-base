package com.weekend.mango.controllers;

import com.weekend.mango.models.MangaUser;
import com.weekend.mango.services.MangaUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/mangauser")
public class MangaUserController {

    private MangaUserService mangaUserService;

    public MangaUserController(MangaUserService mangaUserService) {
        this.mangaUserService = mangaUserService;
    }

    @GetMapping("/manga/{mangaId}/user/{userId}")
    public ResponseEntity<MangaUser> getMangaUserByMangaAndUser(@PathVariable Long mangaId, @PathVariable Long userId) throws Exception {

        MangaUser mangaUser = mangaUserService.getMangaUserByMangaAndUser(mangaId,userId);

        return ResponseEntity.ok(mangaUser);
    }

}

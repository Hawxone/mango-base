package com.weekend.mango.controllers;

import com.google.gson.*;
import com.weekend.mango.entities.UserEntity;
import com.weekend.mango.models.*;
import com.weekend.mango.models.Character;
import com.weekend.mango.repositories.UserEntityRepository;
import com.weekend.mango.services.MangaService;

import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/manga")
public class MangaController {

    private final MangaService mangaService;
    private final UserEntityRepository userEntityRepository;


    public MangaController(MangaService mangaService, UserEntityRepository userEntityRepository) {
        this.mangaService = mangaService;
        this.userEntityRepository = userEntityRepository;

    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getMangas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "24") int size,@RequestParam(defaultValue = "0") Long userId){

        Map<String, Object> mangaList =mangaService.getPaginatedMangaList(page,size,userId);

        return ResponseEntity.ok(mangaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manga> getMangaById(@PathVariable Long id, @RequestParam(defaultValue = "0") Long userId) throws Exception{

        Manga mangaDetail = mangaService.getMangaById(id,userId);

        return ResponseEntity.ok(mangaDetail);
    }

    @PostMapping
    public ResponseEntity<Manga> createManga(@RequestParam Map<String,String> requests) throws Exception {

        try {
            Manga mangaModel = new Manga();
            Optional<UserEntity> fetchUser = userEntityRepository.findById(Long.valueOf(requests.get("userId")));

            if (fetchUser.isPresent()){

                String title = "Draft";

                UserModel createdBy= UserModel.builder()
                        .id(fetchUser.get().getId())
                        .username(fetchUser.get().getUsername())
                        .imageUrl(fetchUser.get().getImageUrl())
                        .build();

                DateTime createdAt = DateTime.now();

                mangaModel.setTitle(title);
                mangaModel.setCreatedBy(createdBy);
                mangaModel.setCreatedAt(createdAt);
                mangaModel = mangaService.createManga(mangaModel);

                return ResponseEntity.ok(mangaModel);
            }


            return null;


        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Manga> saveManga(@PathVariable Long id,@RequestParam Map<String,String> request) throws Exception {

        String title= request.get("title");
        String mangaOrder= request.get("mangaOrder");
        String createdAt= request.get("createdAt");
        String createdBy= request.get("createdBy");
        String uploadDate= request.get("uploadDate");
        String artist = request.get("artist");
        String category = request.get("category");
        String comment = request.get("comment");
        String mangaUser = request.get("mangaUser");
        String group = request.get("group");
        String character = request.get("character");
        String tag = request.get("tag");
        String parody = request.get("parody");

        JsonObject userJSON = new Gson().fromJson(createdBy,JsonObject.class);
        JsonArray artistJSON = new Gson().fromJson(artist, JsonArray.class);
        JsonArray categoryJSON = new Gson().fromJson(category, JsonArray.class);
        JsonArray commentJSON = new Gson().fromJson(comment, JsonArray.class);
        JsonArray mangaUserJSON = new Gson().fromJson(mangaUser, JsonArray.class);
        JsonArray groupJSON = new Gson().fromJson(group, JsonArray.class);
        JsonArray characterJSON = new Gson().fromJson(character, JsonArray.class);
        JsonArray tagJSON = new Gson().fromJson(tag, JsonArray.class);
        JsonArray parodyJSON = new Gson().fromJson(parody, JsonArray.class);

        UserModel userModel = new UserModel();
        userModel.setId(userJSON.get("id").getAsLong());
        userModel.setUsername(userJSON.get("username").getAsString());
        userModel.setImageUrl(userJSON.get("imageUrl").getAsString());

        List<Artist> artistList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();
        List<MangaUser> mangaUserList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        List<Parody> parodyList = new ArrayList<>();

        if (!artist.isBlank()){
            for (JsonElement a:artistJSON
            ) {
                Artist artistModel = new Artist();
                artistModel.setId(a.getAsJsonObject().get("id").getAsLong());
                artistModel.setName(a.getAsJsonObject().get("name").getAsString());
                artistList.add(artistModel);
            }
        }

        if (!category.isBlank()){
            for (JsonElement a:categoryJSON
            ) {
                Category categoryModel = new Category();
                categoryModel.setId(a.getAsJsonObject().get("id").getAsLong());
                categoryModel.setName(a.getAsJsonObject().get("name").getAsString());
                categoryList.add(categoryModel);
            }
        }

        if (!comment.isBlank()){
            for (JsonElement a:commentJSON
            ) {
                Comment commentModel = new Comment();
                commentModel.setId(a.getAsJsonObject().get("id").getAsLong());
                commentModel.setComment(a.getAsJsonObject().get("comment").getAsString());
                commentModel.setUser(userModel);
                commentList.add(commentModel);
            }
        }

        if (!mangaUser.isBlank()){
            for (JsonElement a:mangaUserJSON
            ) {
                MangaUser mangaUserModel = new MangaUser();
                mangaUserModel.setId(a.getAsJsonObject().get("id").getAsLong());
                mangaUserModel.setCurrentPage(a.getAsJsonObject().get("currentPage").getAsInt());
                mangaUserModel.setIsFavorite(a.getAsJsonObject().get("isFavorite").getAsBoolean());
                mangaUserModel.setIsWillRead(a.getAsJsonObject().get("isWillRead").getAsBoolean());
                mangaUserList.add(mangaUserModel);
            }
        }


        if (!group.isBlank()){
            for (JsonElement a:groupJSON
            ) {
                Group groupModel = new Group();
                groupModel.setId(a.getAsJsonObject().get("id").getAsLong());
                groupModel.setName(a.getAsJsonObject().get("name").getAsString());
                groupList.add(groupModel);
            }
        }

        if (!character.isBlank()){
            for (JsonElement a:characterJSON
            ) {
                Character characterModel = new Character();
                characterModel.setId(a.getAsJsonObject().get("id").getAsLong());
                characterModel.setName(a.getAsJsonObject().get("name").getAsString());
                characterList.add(characterModel);
            }
        }

        if (!tag.isBlank()){
            for (JsonElement a:tagJSON
            ) {
                Tag tagModel = new Tag();
                tagModel.setId(a.getAsJsonObject().get("id").getAsLong());
                tagModel.setName(a.getAsJsonObject().get("name").getAsString());
                tagList.add(tagModel);
            }
        }

        if (!parody.isBlank()){
            for (JsonElement a:parodyJSON
            ) {
                Parody parodyModel = new Parody();
                parodyModel.setId(a.getAsJsonObject().get("id").getAsLong());
                parodyModel.setName(a.getAsJsonObject().get("name").getAsString());
                parodyList.add(parodyModel);
            }
        }

        Manga mangaModel = new Manga();
        mangaModel.setId(id);
        mangaModel.setTitle(title);
        if (Objects.equals(mangaOrder, "")){
            mangaModel.setMangaOrder(null);
        }else{
            mangaModel.setMangaOrder(Integer.parseInt(mangaOrder));
        }
        if (Objects.equals(uploadDate, "")){
            mangaModel.setUploadDate(null);
        }else{
            mangaModel.setUploadDate(DateTime.parse(uploadDate));
        }
        if (Objects.equals(createdAt, "")){
            mangaModel.setCreatedAt(null);
        }else{
            mangaModel.setCreatedAt(DateTime.parse(createdAt));
        }
        mangaModel.setArtist(artistList);
        mangaModel.setCategory(categoryList);
        mangaModel.setComment(commentList);
        mangaModel.setMangaUser(mangaUserList);
        mangaModel.setGroup(groupList);
        mangaModel.setCharacter(characterList);
        mangaModel.setTag(tagList);
        mangaModel.setParody(parodyList);

        mangaModel= mangaService.saveManga(id,mangaModel);


        return ResponseEntity.ok(mangaModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteManga(@PathVariable Long id) throws Exception {
        boolean deleted;
        deleted = mangaService.deleteManga(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }
}
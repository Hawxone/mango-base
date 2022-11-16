package com.weekend.mango.services;

import com.weekend.mango.entities.*;
import com.weekend.mango.models.*;
import com.weekend.mango.models.Character;
import com.weekend.mango.repositories.*;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class MangaServiceImpl implements MangaService{

    private final MangaEntityRepository mangaEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ArtistEntityRepository artistEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final CharacterEntityRepository characterEntityRepository;
    private final GroupEntityRepository groupEntityRepository;
    private final ParodyEntityRepository parodyEntityRepository;
    private final TagEntityRepository tagEntityRepository;
    private final UserTagEntityRepository userTagEntityRepository;
    private final PageEntityRepository pageEntityRepository;

    private final MangaUserService mangaUserService;



    public MangaServiceImpl(MangaEntityRepository mangaEntityRepository, UserEntityRepository userEntityRepository, ArtistEntityRepository artistEntityRepository, CategoryEntityRepository categoryEntityRepository, CharacterEntityRepository characterEntityRepository, GroupEntityRepository groupEntityRepository, ParodyEntityRepository parodyEntityRepository, TagEntityRepository tagEntityRepository, UserTagEntityRepository userTagEntityRepository, PageEntityRepository pageEntityRepository, MangaUserService mangaUserService) {
        this.mangaEntityRepository = mangaEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.artistEntityRepository = artistEntityRepository;
        this.categoryEntityRepository = categoryEntityRepository;
        this.characterEntityRepository = characterEntityRepository;
        this.groupEntityRepository = groupEntityRepository;
        this.parodyEntityRepository = parodyEntityRepository;
        this.tagEntityRepository = tagEntityRepository;
        this.userTagEntityRepository = userTagEntityRepository;
        this.pageEntityRepository = pageEntityRepository;
        this.mangaUserService = mangaUserService;
    }

    private Optional<UserEntity> getUser(Long id){
        return userEntityRepository.findById(id);
    }
    private Optional<ArtistEntity> getArtist(Long id){
        return artistEntityRepository.findById(id);
    }
    private Optional<CategoryEntity> getCategory(Long id){
        return categoryEntityRepository.findById(id);
    }
    private Optional<CharacterEntity> getCharacter(Long id){
        return characterEntityRepository.findById(id);
    }
    private Optional<GroupEntity> getGroup(Long id){
        return groupEntityRepository.findById(id);
    }
    private Optional<ParodyEntity> getParody(Long id){
        return parodyEntityRepository.findById(id);
    }
    private Optional<TagEntity> getTag(Long id){
        return tagEntityRepository.findById(id);
    }

    private Manga getManga(Optional<MangaEntity> mangaEntity,Long userId) throws Exception {

        try {

            UserModel userModel = new UserModel();
            MangaUser mangaUser = new MangaUser();


            List<Artist> artistList = new ArrayList<>();
            List<Category> categoryList = new ArrayList<>();
            List<Comment> commentList = new ArrayList<>();
            List<Group> groupList = new ArrayList<>();
            List<Character> characterList = new ArrayList<>();
            List<Tag> tagList = new ArrayList<>();
            List<Parody> parodyList = new ArrayList<>();
            List<UserTagEntity> likedUserTagEntities = new ArrayList<>();
            List<UserTagEntity> dislikedUserTagEntities = new ArrayList<>();

            if (mangaEntity.isPresent()){
                Optional<UserEntity> user = getUser(mangaEntity.get().getCreatedBy().getId());
                Optional<UserEntity> checkUser = getUser(userId);


                if (checkUser.isPresent()){
                    mangaUser=mangaUserService.getMangaUserByMangaAndUser(mangaEntity.get().getId(),checkUser.get().getId());
                    likedUserTagEntities = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsTrue(checkUser.get().getId());
                    dislikedUserTagEntities = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsFalse(checkUser.get().getId());
                }

                if (user.isPresent()){
                    userModel.setUsername(user.get().getUsername());
                    userModel.setImageUrl(user.get().getImageUrl());
                    userModel.setId(user.get().getId());
                }else {
                    throw new Exception("no user found");
                }


                for (ArtistEntity a:mangaEntity.get().getArtist()
                ) {
                    Long mangaCountByArtistTag = mangaEntityRepository.countMangaEntitiesByArtistId(a.getId());
                    Artist artist = new Artist();
                    artist.setId(a.getId());
                    artist.setName(a.getName());
                    artist.setMangaCount(mangaCountByArtistTag);
                    artistList.add(artist);
                }

                for (CategoryEntity a:mangaEntity.get().getCategory()
                ) {
                    Long mangaCountByCategoryTag= mangaEntityRepository.countMangaEntitiesByCategoryId(a.getId());
                    Category category = new Category();
                    category.setId(a.getId());
                    category.setName(a.getName());
                    category.setMangaCount(mangaCountByCategoryTag);
                    categoryList.add(category);
                }

                for (CommentEntity a:mangaEntity.get().getComment()
                ) {
                    UserModel commentUserModel = new UserModel();
                    commentUserModel.setId(a.getUser().getId());
                    commentUserModel.setUsername(a.getUser().getUsername());
                    commentUserModel.setImageUrl(a.getUser().getImageUrl());

                    Comment comment = new Comment();
                    comment.setId(a.getId());
                    comment.setCreatedAt(a.getCreatedAt());
                    comment.setComment(a.getComment());
                    comment.setUser(commentUserModel);
                    commentList.add(comment);
                }


                for (GroupEntity a:mangaEntity.get().getGroup()
                ) {
                    Long mangaCountByGroupTag = mangaEntityRepository.countMangaEntitiesByGroupId(a.getId());
                    Group group = new Group();
                    group.setId(a.getId());
                    group.setName(a.getName());
                    group.setMangaCount(mangaCountByGroupTag);
                    groupList.add(group);
                }

                for (CharacterEntity a:mangaEntity.get().getCharacter()
                ) {
                    Long mangaCountByCharacterTag = mangaEntityRepository.countMangaEntitiesByCharacterId(a.getId());
                    Character character = new Character();
                    character.setId(a.getId());
                    character.setName(a.getName());
                    character.setMangaCount(mangaCountByCharacterTag);
                    characterList.add(character);
                }

                for (TagEntity a:mangaEntity.get().getTag()
                ) {

                    Long mangaCountByTag = mangaEntityRepository.countMangaEntitiesByTagId(a.getId());
                    Tag tag = new Tag();
                    tag.setId(a.getId());
                    tag.setName(a.getName());
                    tag.setMangaCount(mangaCountByTag);
                    if (likedUserTagEntities.size()>0){
                        for (UserTagEntity u:likedUserTagEntities
                             ) {
                            if (a.getId().equals(u.getTag().getId())){

                                tag.setIsLiked(true);
                            }
                        }
                    }

                    if (dislikedUserTagEntities.size()>0) {
                        for (UserTagEntity u:dislikedUserTagEntities
                        ) {
                            if (a.getId().equals(u.getTag().getId())){
                                tag.setIsLiked(false);

                            }
                        }
                    }

                    tagList.add(tag);



                }

                for (ParodyEntity a:mangaEntity.get().getParody()
                ) {
                    Long mangaCountByParody = mangaEntityRepository.countMangaEntitiesByParodyId(a.getId());
                    Parody parody = new Parody();
                    parody.setId(a.getId());
                    parody.setName(a.getName());
                    parody.setMangaCount(mangaCountByParody);
                    parodyList.add(parody);
                }

                Long pageCount = pageEntityRepository.countPageEntitiesByMangaId_Id(mangaEntity.get().getId());
                Manga manga = new Manga();

                manga.setId(mangaEntity.get().getId());
                manga.setTitle(mangaEntity.get().getTitle());
                manga.setMangaOrder(mangaEntity.get().getMangaOrder());
                manga.setCreatedAt(mangaEntity.get().getCreatedAt());
                manga.setCreatedBy(userModel);
                manga.setPageCount(pageCount);
                manga.setUploadDate(mangaEntity.get().getUploadDate());
                manga.setArtist(artistList);
                manga.setCategory(categoryList);
                manga.setComment(commentList);
                manga.setGroup(groupList);
                manga.setCharacter(characterList);
                manga.setTag(tagList);
                manga.setParody(parodyList);
                if (checkUser.isPresent()){
                    manga.setMangaUser(mangaUser);
                }
                return manga;
            }else {
                throw new Exception("can't find entry");
            }

        }catch (Exception e){
            throw   new Exception("can't find entry" + e);
        }

    }

    private Map<String, Object> getMangaList(Long userId, List<MangaList> mangaLists, Page<MangaEntity> mangaEntities) {

        for (MangaEntity a:mangaEntities
        ) {
            Long pageCount = pageEntityRepository.countPageEntitiesByMangaId_Id(a.getId());
            MangaList mangaList = new MangaList();
            mangaList.setId(a.getId());
            mangaList.setTitle(a.getTitle());
            mangaList.setOrderId(a.getMangaOrder());
            mangaList.setPageCount(pageCount);

            Optional<UserEntity> fetchUser = getUser(userId);

            if (fetchUser.isPresent()){
                int likedTagCount = 0;
                int dislikedTagCount = 0;

                List<UserTagEntity> likedTag = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsTrue(userId);
                List<UserTagEntity> dislikedTag = userTagEntityRepository.findUserTagEntitiesByUser_IdAndIsLikeIsFalse(userId);

                if (dislikedTag!=null){
                    for (TagEntity t:a.getTag()
                    ) {
                        for (UserTagEntity u:dislikedTag
                             ) {
                            if (t.getId().equals(u.getTag().getId())){
                                dislikedTagCount++;
                            }
                        }
                    }
                }

               if (likedTag!=null){
                   for (TagEntity t:a.getTag()
                   ) {
                       for (UserTagEntity u:likedTag
                       ) {
                           if (t.getId().equals(u.getTag().getId())){
                               likedTagCount++;
                           }
                       }
                   }
               }
               
               if (dislikedTagCount>0){
                   mangaList.setVerdict("disliked");
               } else if (likedTagCount>0) {
                   mangaList.setVerdict("liked");
               }else{
                   mangaList.setVerdict("unknown");
               }

            }else {
                mangaList.setVerdict("unknown");
            }

            mangaLists.add(mangaList);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("mangaList",mangaLists);
        response.put("currentPage",mangaEntities.getNumber());
        response.put("totalItems",mangaEntities.getTotalElements());
        response.put("totalPages",mangaEntities.getTotalPages());

        return response;
    }



    @Override
    public Map<String, Object> getMangaResultList(int page, int size, String query,Long userId) {
        List<MangaList>mangaLists =new ArrayList<>();

        Pageable paging = PageRequest.of(page,size).withSort(Sort.by(Sort.Direction.DESC,"mangaOrder"));
        Page<MangaEntity> mangaEntities = mangaEntityRepository.searchManga(query,paging);
        return getMangaList(userId, mangaLists, mangaEntities);
    }

    @Override
    public Map<String, Object> getMangasByTagName(int page, int size, String query, String tagType, Long userId) {

        List<MangaList> mangaLists = new ArrayList<>();

        Pageable paging = PageRequest.of(page,size);
        Page<MangaEntity> mangaEntities;

        Map<String,Object> response  = new HashMap<>();

        switch (tagType) {
            case "artist" -> {

                mangaEntities = mangaEntityRepository.getMangaEntitiesByArtistNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
            case "category" -> {
                mangaEntities = mangaEntityRepository.getMangaEntitiesByCategoryNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
            case "character" -> {
                mangaEntities = mangaEntityRepository.getMangaEntitiesByCharacterNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
            case "group" -> {
                mangaEntities = mangaEntityRepository.getMangaEntitiesByGroupNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
            case "parody" -> {
                mangaEntities = mangaEntityRepository.getMangaEntitiesByParodyNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
            case "tag" -> {
                mangaEntities = mangaEntityRepository.getMangaEntitiesByTagNameIgnoreCase(query, paging);
                response = getMangaList(userId, mangaLists, mangaEntities);
            }
        }
        return response;
    }



    @Override
    public Map<String, Object> getPaginatedMangaList(int page, int size, Long userId) {
        List<MangaList>mangaLists =new ArrayList<>();

        Pageable paging = PageRequest.of(page,size).withSort(Sort.by(Sort.Direction.DESC,"mangaOrder"));

        Page<MangaEntity> mangaEntities = mangaEntityRepository.findAllByMangaOrderNotNull(paging);

        return getMangaList(userId, mangaLists, mangaEntities);
    }


    @Override
    public Map<String, Object> getPaginatedMangaListByUser(int page, int size, Long userId) {
        List<MangaList>mangaLists =new ArrayList<>();

        Pageable paging = PageRequest.of(page,size);
        Page<MangaEntity> mangaEntities = mangaEntityRepository.findMangaEntitiesByCreatedBy_Id(userId,paging);

        for (MangaEntity a:mangaEntities
        ) {
            Long pageCount = pageEntityRepository.countPageEntitiesByMangaId_Id(a.getId());
            MangaList mangaList = new MangaList();
            mangaList.setId(a.getId());
            mangaList.setTitle(a.getTitle());
            mangaList.setOrderId(a.getMangaOrder());
            mangaList.setPageCount(pageCount);

            mangaLists.add(mangaList);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("mangaList",mangaLists);
        response.put("currentPage",mangaEntities.getNumber());
        response.put("totalItems",mangaEntities.getTotalElements());
        response.put("totalPages",mangaEntities.getTotalPages());

        return response;
    }

    @Override
    public boolean publishManga(Long id) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = mangaEntityRepository.findById(id);
            if (fetchManga.isPresent()){
                MangaEntity mangaEntity = fetchManga.get();
                mangaEntity.setUploadDate(DateTime.now());
                Optional<List<MangaEntity>> order = mangaEntityRepository.findAllByMangaOrderNotNull(Sort.by("mangaOrder").descending());
                if (order.isPresent()){
                    if (order.get().size() == 0){
                        mangaEntity.setMangaOrder(1);
                    }else{
                        mangaEntity.setMangaOrder(order.get().get(0).getMangaOrder()+1);
                    }
                }

                mangaEntityRepository.save(mangaEntity);
            }
        }catch (Exception e){
            throw new Exception("can't publish entry " + e);
        }


        return true;
    }



    @Override
    public Manga createManga(Manga mangaModel) throws Exception {

        MangaEntity mangaEntity = new MangaEntity();

        Optional<UserEntity> user = getUser(mangaModel.getCreatedBy().getId());

        if (user.isPresent()){
            try {
                mangaEntity.setTitle(mangaModel.getTitle());
                mangaEntity.setCreatedAt(mangaModel.getCreatedAt());
                mangaEntity.setCreatedBy(user.get());

                mangaEntityRepository.save(mangaEntity);

                mangaModel.setId(mangaEntity.getId());

            }catch (Exception e){
                throw new Exception("can't save entry " + e);
            }
        }else {
            throw new Exception("user not found");
        }


        return mangaModel;
    }

    @Override
    public Manga getMangaByOrderId(Integer id, Long userId) throws Exception {

        Optional<MangaEntity> mangaEntity = mangaEntityRepository.findMangaEntityByMangaOrder(id);

        Manga manga;
        manga = getManga(mangaEntity,userId);

        return manga;
    }


    @Override
    public Manga getMangaById(Long id, Long userId) throws Exception {
        Optional<MangaEntity> mangaEntity = mangaEntityRepository.findById(id);
        Manga manga;
        manga = getManga(mangaEntity,userId);

        return manga;

    }

    @Override
    public Manga saveManga(Long id,Manga mangaModel) throws Exception {

        try {
            Optional<MangaEntity> fetchManga = mangaEntityRepository.findById(id);
            List<ArtistEntity> artistEntities = new ArrayList<>();
            List<CategoryEntity> categoryEntities = new ArrayList<>();
            List<CharacterEntity> characterEntities = new ArrayList<>();
            List<GroupEntity> groupEntities = new ArrayList<>();
            List<ParodyEntity> parodyEntities = new ArrayList<>();
            List<TagEntity> tagEntities = new ArrayList<>();

            fetchManga.ifPresent(mangaEntity -> {
                Optional<UserEntity> fetchUser = getUser(mangaEntity.getCreatedBy().getId());

                for (Artist a:mangaModel.getArtist()
                     ) {
                    Optional<ArtistEntity> fetchArtist = getArtist(a.getId());
                    if (fetchArtist.isPresent()){
                        ArtistEntity artistEntity = new ArtistEntity();
                        artistEntity.setId(fetchArtist.get().getId());
                        artistEntity.setName(fetchArtist.get().getName());
                        artistEntities.add(artistEntity);
                    }else{
                        try {
                            throw new Exception("no artist found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Category a:mangaModel.getCategory()
                ) {
                    Optional<CategoryEntity> fetchCategory = getCategory(a.getId());
                    if (fetchCategory.isPresent()){
                        CategoryEntity categoryEntity = new CategoryEntity();
                        categoryEntity.setId(fetchCategory.get().getId());
                        categoryEntity.setName(fetchCategory.get().getName());
                        categoryEntities.add(categoryEntity);
                    }else{
                        try {
                            throw new Exception("no category found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Character a:mangaModel.getCharacter()
                ) {
                    Optional<CharacterEntity> fetchCharacter = getCharacter(a.getId());
                    if (fetchCharacter.isPresent()){
                        CharacterEntity characterEntity = new CharacterEntity();
                        characterEntity.setId(fetchCharacter.get().getId());
                        characterEntity.setName(fetchCharacter.get().getName());
                        characterEntities.add(characterEntity);
                    }else{
                        try {
                            throw new Exception("no character found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Group a:mangaModel.getGroup()
                ) {
                    Optional<GroupEntity> fetchGroup = getGroup(a.getId());
                    if (fetchGroup.isPresent()){
                        GroupEntity groupEntity = new GroupEntity();
                        groupEntity.setId(fetchGroup.get().getId());
                        groupEntity.setName(fetchGroup.get().getName());
                        groupEntities.add(groupEntity);
                    }else{
                        try {
                            throw new Exception("no group found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Parody a:mangaModel.getParody()
                ) {
                    Optional<ParodyEntity> fetchParody = getParody(a.getId());
                    if (fetchParody.isPresent()){
                        ParodyEntity parodyEntity = new ParodyEntity();
                        parodyEntity.setId(fetchParody.get().getId());
                        parodyEntity.setName(fetchParody.get().getName());
                        parodyEntities.add(parodyEntity);
                    }else{
                        try {
                            throw new Exception("no parody found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (Tag a:mangaModel.getTag()
                ) {
                    Optional<TagEntity> fetchTag = getTag(a.getId());
                    if (fetchTag.isPresent()){
                        TagEntity tagEntity = new TagEntity();
                        tagEntity.setId(fetchTag.get().getId());
                        tagEntity.setName(fetchTag.get().getName());
                        tagEntities.add(tagEntity);
                    }else{
                        try {
                            throw new Exception("no tag found!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                mangaEntity.setTitle(mangaModel.getTitle());
                mangaEntity.setMangaOrder(mangaModel.getMangaOrder());
                mangaEntity.setUploadDate(mangaModel.getUploadDate());
                mangaEntity.setArtist(artistEntities);
                mangaEntity.setCategory(categoryEntities);
                mangaEntity.setCharacter(characterEntities);
                mangaEntity.setParody(parodyEntities);
                mangaEntity.setTag(tagEntities);
                mangaEntity.setGroup(groupEntities);

                if (fetchUser.isPresent()){
                    mangaEntity.setCreatedBy(fetchUser.get());
                }else{
                    try {
                        throw new Exception("user not found");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                mangaEntityRepository.save(mangaEntity);
            });


        }catch (Exception e){
            throw new Exception("can't save entry " + e);
        }

        return mangaModel;
    }

    @Override
    public boolean deleteManga(Long id) throws Exception {
        try {
            Optional<MangaEntity> fetchManga = mangaEntityRepository.findById(id);
            fetchManga.ifPresent(mangaEntityRepository::delete);
        }catch (Exception e){
            throw new Exception("can't delete entry" + e);
        }

        return true;
    }


}

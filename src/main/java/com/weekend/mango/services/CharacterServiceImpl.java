package com.weekend.mango.services;

import com.weekend.mango.entities.CharacterEntity;
import com.weekend.mango.models.PageIndex;
import com.weekend.mango.repositories.CharacterEntityRepository;
import com.weekend.mango.repositories.MangaEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.weekend.mango.models.Character;

import java.util.*;

@Service
public class CharacterServiceImpl implements CharacterService{

    private final CharacterEntityRepository characterEntityRepository;
    private final MangaEntityRepository mangaEntityRepository;

    public CharacterServiceImpl(CharacterEntityRepository characterEntityRepository, MangaEntityRepository mangaEntityRepository) {
        this.characterEntityRepository = characterEntityRepository;
        this.mangaEntityRepository = mangaEntityRepository;
    }

    private long findFirst(Iterator<CharacterEntity> iterator, Long id) {

        long index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public List<Character> getCharacters() {

        List<CharacterEntity> characterEntities = characterEntityRepository.findAll();

        return characterEntities.stream().map((characterEntity)->
                Character.builder()
                        .id(characterEntity.getId())
                        .name(characterEntity.getName())
                        .build()
                ).toList();
    }

    @Override
    public Map<String, Object> getPaginatedCharacters(int page, int size) {

        List<Character> characters = new ArrayList<>();
        Pageable paging = PageRequest.of(page,size).withSort(Sort.by("name").ascending());
        Page<CharacterEntity> characterEntities = characterEntityRepository.findAll(paging);
        Iterable<CharacterEntity> characterEntityIterable = characterEntityRepository.findAll(paging.getSort());

        List<PageIndex> pageIndices = new ArrayList<>();

        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ )
        {
            CharacterEntity characterAlphabet = characterEntityRepository.findFirstByNameStartsWith(String.valueOf(alphabet));
            if (characterAlphabet != null){
                PageIndex pageIndex = new PageIndex();
                pageIndex.setName(String.valueOf(alphabet));
                pageIndex.setIndex(findFirst(characterEntityIterable.iterator(),characterAlphabet.getId()));
                pageIndices.add(pageIndex);
            }
        }

        for (CharacterEntity c:characterEntities
             ) {
            Long mangaCount = mangaEntityRepository.countMangaEntitiesByCharacterId(c.getId());

            Character character = new Character();
            character.setId(c.getId());
            character.setName(c.getName());
            character.setMangaCount(mangaCount);
            characters.add(character);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("characterList",characters);
        response.put("currentPage",characterEntities.getNumber());
        response.put("totalItems",characterEntities.getTotalElements());
        response.put("totalPages",characterEntities.getTotalPages());
        response.put("index",pageIndices);

        return response;
    }

    @Override
    public Character saveCharacter(Character characterModel) throws Exception {

        CharacterEntity character = new CharacterEntity();

        try{
            character.setName(characterModel.getName());
            characterEntityRepository.save(character);
            characterModel.setId(character.getId());

        }catch (Exception e){
            throw new Exception("can't save entry "+e);
        }

        return characterModel;
    }

    @Override
    public Character updateCharacter(Long id, Character characterModel) throws Exception {

        try {
            Optional<CharacterEntity> fetchCharacter = characterEntityRepository.findById(id);
            fetchCharacter.ifPresent(characterEntity -> {
                characterEntity.setName(characterModel.getName());
                characterEntityRepository.save(characterEntity);
                characterModel.setName(characterEntity.getName());

            });

        }catch (Exception e){
            throw new Exception("can't update entry " + e);
        }

        return characterModel;
    }

    @Override
    public boolean deleteCharacter(Long id) throws Exception {

        try {
            Optional<CharacterEntity> fetchCharacter = characterEntityRepository.findById(id);
            fetchCharacter.ifPresent(characterEntityRepository::delete);
        }catch (Exception e){
            throw new Exception("can't delete entry " + e);
        }

        return true;
    }


}

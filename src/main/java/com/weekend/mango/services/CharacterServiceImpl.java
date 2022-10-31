package com.weekend.mango.services;

import com.weekend.mango.entities.CharacterEntity;
import com.weekend.mango.repositories.CharacterEntityRepository;
import org.springframework.stereotype.Service;
import com.weekend.mango.models.Character;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService{

    private final CharacterEntityRepository characterEntityRepository;

    public CharacterServiceImpl(CharacterEntityRepository characterEntityRepository) {
        this.characterEntityRepository = characterEntityRepository;
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

package com.weekend.mango.services;

import com.weekend.mango.models.Character;
import java.util.List;
import java.util.Map;

public interface CharacterService {
    List<Character> getCharacters();

    Character saveCharacter(Character characterModel) throws Exception;

    Character updateCharacter(Long id, Character characterModel) throws Exception;

    boolean deleteCharacter(Long id) throws Exception;

    Map<String, Object> getPaginatedCharacters(int page, int size);
}

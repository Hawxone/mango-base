package com.weekend.mango.controllers;

import com.weekend.mango.models.Character;
import com.weekend.mango.services.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/character")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<Character>> getCharacters(){

        List<Character> characters = characterService.getCharacters();

        return ResponseEntity.ok(characters);
    }

    @PostMapping
    public ResponseEntity<Character> saveCharacter(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Character characterModel = Character.builder()
                .name(name)
                .build();

        characterModel = characterService.saveCharacter(characterModel);

        return ResponseEntity.ok(characterModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestParam Map<String,String> request) throws Exception {

        String name = request.get("name");

        Character characterModel = Character.builder()
                .name(name)
                .build();

        characterModel = characterService.updateCharacter(id,characterModel);

        return ResponseEntity.ok(characterModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCharacter(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted = characterService.deleteCharacter(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }
}

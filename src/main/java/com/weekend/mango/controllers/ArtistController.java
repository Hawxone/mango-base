package com.weekend.mango.controllers;

import com.weekend.mango.models.Artist;
import com.weekend.mango.services.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {

    private ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getArtists(){

        List<Artist> artistList = artistService.getArtists();

        return ResponseEntity.ok(artistList);
    }

    @PostMapping
    public ResponseEntity<Artist> saveArtist(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Artist artistModel =Artist.builder()
                .name(name)
                .build();

        artistModel = artistService.saveArtist(artistModel);

        return ResponseEntity.ok(artistModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id,@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Artist artistModel =Artist.builder()
                .name(name)
                .build();

        artistModel = artistService.updateArtist(id,artistModel);

    return ResponseEntity.ok(artistModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteArtist(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted =artistService.deleteArtist(id);
        Map<String,Boolean> response =  new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }



}

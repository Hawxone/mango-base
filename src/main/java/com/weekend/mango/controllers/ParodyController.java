package com.weekend.mango.controllers;

import com.weekend.mango.models.Parody;
import com.weekend.mango.services.ParodyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/parody")
public class ParodyController {

    private ParodyService parodyService;


    public ParodyController(ParodyService parodyService) {
        this.parodyService = parodyService;
    }

    @GetMapping
    public ResponseEntity<List<Parody>> getParodies(){

        List<Parody> parodies = parodyService.getParodies();

        return ResponseEntity.ok(parodies);
    }

    @PostMapping
    public ResponseEntity<Parody> saveParody(@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Parody parodyModel = Parody.builder()
                .name(name)
                .build();

        parodyModel = parodyService.saveParody(parodyModel);

        return ResponseEntity.ok(parodyModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parody> updateParody(@PathVariable Long id,@RequestParam Map<String,String> request) throws Exception{

        String name = request.get("name");

        Parody parodyModel = Parody.builder()
                .name(name)
                .build();

        parodyModel = parodyService.updateParody(id,parodyModel);

        return ResponseEntity.ok(parodyModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteParody(@PathVariable Long id) throws Exception {

        boolean deleted;
        deleted = parodyService.deleteParody(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);

        return ResponseEntity.ok(response);
    }
}

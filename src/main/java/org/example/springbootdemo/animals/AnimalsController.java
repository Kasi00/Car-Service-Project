package org.example.springbootdemo.animals;


import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.animals.model.AnimalDTO;
import org.example.springbootdemo.animals.model.AnimalRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalsController {

    private final AnimalsService animalsService;


    @GetMapping
    Page<AnimalDTO> getAnimals(@RequestParam int page, @RequestParam int pageSize, @RequestParam String type) {
        return animalsService.getAnimals(page, pageSize,type);
    }

    @PostMapping
    ResponseEntity<Void> createAnimal(@RequestBody AnimalRequest animalRequest) {
        animalsService.createAnimal(animalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping
    AnimalDTO updateAnimal(@PathVariable long id,@RequestBody AnimalRequest request) {
        return animalsService.updateAnimal(id, request);

    }


    @DeleteMapping
    ResponseEntity<Void> deleteAnimal(@PathVariable long id) {
        animalsService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }







}

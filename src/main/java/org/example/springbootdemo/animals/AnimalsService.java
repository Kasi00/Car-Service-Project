package org.example.springbootdemo.animals;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.animals.model.AnimalDTO;
import org.example.springbootdemo.animals.model.AnimalRequest;
import org.example.springbootdemo.animals.model.ZooDTO;
import org.example.springbootdemo.animals.persistance.Animal;
import org.example.springbootdemo.animals.persistance.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimalsService {
    private final AnimalRepository animalRepository;


    public Page<AnimalDTO> getAnimals(int page, int pageSize, String type){
        return animalRepository.findAnimals(type,org.springframework.data.domain.PageRequest.of(page, pageSize));
    }
    public AnimalDTO getAnimal(long id){
        Animal animal=animalRepository.findById(id).get();
        return mapAnimal(animal);
    }

    private AnimalDTO mapAnimal(Animal animal) {
        return new AnimalDTO(animal.getId(),animal.getName(),animal.getType());
    }



    public void createAnimal(AnimalRequest animalRequest) {
        Animal animal=new Animal();
        animal.setType(animalRequest.getType());
        animal.setName(animalRequest.getName());
        animalRepository.save(animal);
    }

    public AnimalDTO updateAnimal(long id, AnimalRequest request) {
        Animal animal=animalRepository.findById(id).get();
        animal.setName(request.getName());
        animal.setType(request.getType());
        animalRepository.save(animal);
        return mapAnimal(animal);
    }


    public void deleteAnimal(long id) {
        animalRepository.deleteById(id);
    }


    public Animal findAnimal(Long id){
        return animalRepository.findById(id).get();
    }


















}

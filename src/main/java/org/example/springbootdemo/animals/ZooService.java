package org.example.springbootdemo.animals;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.animals.model.AnimalDTO;
import org.example.springbootdemo.animals.model.ZooDTO;
import org.example.springbootdemo.animals.model.ZooRequest;
import org.example.springbootdemo.animals.persistance.Animal;
import org.example.springbootdemo.animals.persistance.Zoo;
import org.example.springbootdemo.animals.persistance.ZooRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZooService {
    private final ZooRepository zooRepository;
    private final AnimalsService animalsService;



    public Page<ZooDTO> getZoos(int page,int pageSize){
        return zooRepository.findZoo(Pageable.ofSize(pageSize).withPage(page));
    }


    public void addZoo(ZooRequest zooRequest){
        Zoo zoo=new Zoo();
        zoo.setName(zooRequest.getName());
        zoo.setAmountAnimals(zooRequest.getAmountAnimals());
        zoo.setAnimal(animalsService.findAnimal(zooRequest.getAnimalId()));
    }


    public void updateZoo(long id,ZooRequest zooRequest){
        Zoo zoo=zooRepository.findById(id).get();
        zoo.setName(zooRequest.getName());
        zoo.setAmountAnimals(zooRequest.getAmountAnimals());
        if(zooRequest.getAnimalId()!=null){
            zoo.setAnimal(animalsService.findAnimal(zooRequest.getAnimalId()));
        }
        zooRepository.save(zoo);
    }


    public void deleteZoo(long id){
        zooRepository.deleteById(id);
    }

    public ZooDTO findZooById(Long id){
        Zoo zoo=zooRepository.findById(id).get();
        return mapZoo(zoo);
    }

    public ZooDTO mapZoo(Zoo zoo){
        return new ZooDTO(zoo.getId(),zoo.getAmountAnimals(),zoo.getName(),new AnimalDTO(zoo.getAnimal().getId(),zoo.getAnimal().getName(),zoo.getAnimal().getType()));
    }

    public Animal findAnimal(Long id){
        return animalsService.findAnimal(id);
    }


}

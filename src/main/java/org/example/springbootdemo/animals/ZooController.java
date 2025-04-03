package org.example.springbootdemo.animals;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.animals.model.ZooDTO;
import org.example.springbootdemo.animals.model.ZooRequest;
import org.example.springbootdemo.animals.persistance.ZooRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo")
@RequiredArgsConstructor
public class ZooController {
    private final ZooService zooService;
    private final ZooRepository zooRepository;



    @GetMapping
    Page<ZooDTO> getZoos(@RequestParam int page, @RequestParam int pageSize){
        return zooService.getZoos(page,pageSize);
    }


    @PostMapping
    void addZoo(@RequestBody ZooRequest zooRequest){
        zooService.addZoo(zooRequest);
    }

    @PutMapping
    void updateZoo(@PathVariable long id,@RequestBody ZooRequest zooRequest){
        zooService.updateZoo(id,zooRequest);
    }

    @DeleteMapping
    ResponseEntity<Void> deleteZoo(@PathVariable long id){
        zooService.deleteZoo(id);
        return ResponseEntity.noContent().build();
    }

}

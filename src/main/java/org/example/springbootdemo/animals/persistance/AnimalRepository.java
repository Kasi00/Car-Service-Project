package org.example.springbootdemo.animals.persistance;

import org.example.springbootdemo.animals.model.AnimalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("Select new org.example.springbootdemo.animals.model.AnimalDTO(a.id,a.name,a.type)From Animal  a where a.type= :type")
    Page<AnimalDTO> findAnimals(String type, Pageable pageable);

}

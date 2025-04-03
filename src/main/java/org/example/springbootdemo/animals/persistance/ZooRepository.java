package org.example.springbootdemo.animals.persistance;

import org.example.springbootdemo.animals.model.ZooDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZooRepository extends JpaRepository<Zoo, Long> {
    @Query("SELECT NEW org.example.springbootdemo.animals.model.ZooDTO(" +
            "z.id, z.amountAnimals, z.name, " +
            "NEW org.example.springbootdemo.animals.model.AnimalDTO(a.id, a.name, a.type)) " +
            "FROM Zoo z JOIN z.animal a")
    Page<ZooDTO> findZoo(Pageable pageable);
}

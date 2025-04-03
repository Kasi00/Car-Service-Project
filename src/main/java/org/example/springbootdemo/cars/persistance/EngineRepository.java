package org.example.springbootdemo.cars.persistance;

import org.example.springbootdemo.cars.model.EngineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EngineRepository  extends JpaRepository<Engine, Long> {

    @Query("SELECT NEW org.example.springbootdemo.cars.model.EngineDTO(e.id,e.horsePower,e.capacity)FROM Engine e where e.capacity= :capacity")
    Page<EngineDTO> findEngines(double capacity, Pageable pageable);



}

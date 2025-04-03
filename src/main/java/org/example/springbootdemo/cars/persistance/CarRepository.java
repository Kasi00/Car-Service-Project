package org.example.springbootdemo.cars.persistance;

import org.example.springbootdemo.cars.model.CarDTO;
import org.example.springbootdemo.cars.model.OwnerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT new org.example.springbootdemo.cars.model.CarDTO(" +
            "c.id, c.model, c.year, c.drivable,c.priceInCents,c.imageUrl,"+
            "new org.example.springbootdemo.cars.model.EngineDTO(e.id, e.horsePower, e.capacity))" +
            "FROM Car c JOIN c.engine e")
    Page<CarDTO> findCars(Pageable pageable);

    @Query("SELECT new org.example.springbootdemo.cars.model.OwnerDTO(u.username) " +
            "FROM Car c JOIN c.owners u WHERE c.id = :id")
    List<OwnerDTO> findOwnersByCarId(@Param("id") Long id);
}

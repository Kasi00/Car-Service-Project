package org.example.springbootdemo.cars;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.model.CarDTO;
import org.example.springbootdemo.cars.model.CarRequest;
import org.example.springbootdemo.cars.model.OwnerDTO;
import org.example.springbootdemo.cars.persistance.Car;
import org.example.springbootdemo.cars.persistance.CarRepository;
import org.example.springbootdemo.cars.user.persistance.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.springbootdemo.cars.security.AuthorizationConstants.ADMIN;
import static org.example.springbootdemo.cars.security.AuthorizationConstants.USER_OR_ADMIN;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarsService carsService;
    private final S3Service s3Service;
    private final CarRepository carRepository;


    @GetMapping
    @PreAuthorize(USER_OR_ADMIN)
    Page<CarDTO> getCars(@RequestParam int page, @RequestParam int pageSize) {
        return carsService.getCars(page, pageSize);
    }

    @GetMapping("/{id}/owners")
    @PreAuthorize(ADMIN)
    public ResponseEntity<List<OwnerDTO>> getCarOwners(@PathVariable Long id) {
        List<OwnerDTO> owners = carRepository.findOwnersByCarId(id);
        return ResponseEntity.ok(owners);
    }


    @GetMapping("/{id}/image")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<String> getCarImageURL(@PathVariable Long id){
        CarDTO car=carsService.findCarById(id);
        if(car.getImageUrl()!=null){
            return ResponseEntity.ok(car.getImageUrl());
        }else{
            return ResponseEntity.badRequest().body("No image found for this car.");
        }
    }

    @GetMapping("{id}")
    @PreAuthorize(USER_OR_ADMIN)
    ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        CarDTO car=carsService.findCarById(id);
        if(car!=null){
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }





    @PostMapping("/buy")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<String> buyCar(@RequestParam Long id, @RequestParam Long userId) {
        try {
            carsService.buyCar(id, userId);
            return ResponseEntity.ok("Car purchased successfully!");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PostMapping("/sell")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<String> sellCar(@RequestParam Long id, @RequestParam Long userId) {
        try{
            carsService.sellCar(id, userId);
            return ResponseEntity.ok("Car sold successfully!");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }







    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<CarDTO> addCar(@RequestBody @Valid CarRequest carRequest) {
        // Call the service to add the car and return the DTO
        CarDTO addedCar = carsService.addCar(carRequest);

        // Return the CarDTO in a ResponseEntity with CREATED status (201)
        return new ResponseEntity<>(addedCar, HttpStatus.CREATED);
    }



    @PutMapping("/price")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> updateCarPrice(@RequestParam Long id, @RequestParam Long priceInCents) {
        try{
            carsService.updateCarPrice(id, priceInCents);
            return ResponseEntity.ok("Car price updated successfully!");
        }catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}/image")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> uploadCarImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String imageUrl = s3Service.uploadFile(file);
        carsService.updateCarImage(id, imageUrl); // განათავსებს სურათის URL დათაბაზაში
        return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
    }




    @PutMapping("{id}")
    @PreAuthorize(ADMIN)
   void updateCar(@PathVariable Long id, @RequestBody @Valid CarRequest request) {
        carsService.updateCar(id,request);
    }





    @DeleteMapping("{id}")
    @PreAuthorize(ADMIN)
   void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }






}

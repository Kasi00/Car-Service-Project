package org.example.springbootdemo.cars;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.model.CarDTO;
import org.example.springbootdemo.cars.model.CarRequest;
import org.example.springbootdemo.cars.model.EngineDTO;
import org.example.springbootdemo.cars.model.OwnerDTO;
import org.example.springbootdemo.cars.persistance.Car;
import org.example.springbootdemo.cars.persistance.CarRepository;
import org.example.springbootdemo.cars.persistance.Engine;
import org.example.springbootdemo.cars.user.persistance.AppUser;
import org.example.springbootdemo.cars.user.persistance.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarRepository carRepository;
    private final EngineService engineService;
    private final AppUserRepository appUserRepository;




    public void buyCar(Long id,Long userId){
        AppUser user=appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found"));
        Car car=carRepository.findById(id).orElseThrow(()->new NotFoundException("Car Not Found"));

        if(user.getBalanceInCents()<car.getPriceInCents()){
           throw new RuntimeException("Sorry, you do not have enough balance to buy it ! ! !");
        }

        user.setBalanceInCents(user.getBalanceInCents()-car.getPriceInCents());
        user.getOwnedCars().add(car);
        car.getOwners().add(user);
        appUserRepository.save(user);
        carRepository.save(car);

    }






    public void sellCar(Long id, Long userId){
        AppUser user=appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User Not Found"));
        Car car=carRepository.findById(id).orElseThrow(()->new NotFoundException("Car Not Found"));

        if(!user.getOwnedCars().contains(car)){
            throw new RuntimeException("User doesnt own car !");
        }
        Long sellingPrice=car.getPriceInCents()*80 /100;
        user.setBalanceInCents(user.getBalanceInCents()+sellingPrice);
        user.getOwnedCars().remove(car);
        car.getOwners().remove(user);
        appUserRepository.save(user);
        carRepository.save(car);

    }



    public void updateCarPrice(Long id,Long priceInCents){
        Car car =carRepository.findById(id).orElseThrow(()->new NotFoundException("Car Not Found"));
        car.setPriceInCents(priceInCents);
        carRepository.save(car);
    }

    public void updateCarImage(Long id, String imageUrl) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setImageUrl(imageUrl);
        carRepository.save(car);
    }


    public List<OwnerDTO> getCarOwners(Long id) {
        return carRepository.findOwnersByCarId(id);
    }



    public Page<CarDTO> getCars(int page, int pageSize) {
        return carRepository.findCars(PageRequest.of(page, pageSize));
    }

    public CarDTO addCar(CarRequest carRequest) {

        Car car = new Car();
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setDrivable(carRequest.isDrivable());
        car.setPriceInCents(carRequest.getPriceInCents());
        car.setImageUrl(carRequest.getImageUrl());


        Engine engine = engineService.findEngine(carRequest.getEngineID());
        car.setEngine(engine);


        car = carRepository.save(car);


        EngineDTO engineDTO = new EngineDTO(
                engine.getId(),
                engine.getHorsePower(),
                engine.getCapacity()
        );


        CarDTO carDTO = new CarDTO(
                car.getId(),
                car.getModel(),
                car.getYear(),
                car.isDrivable(),
                car.getPriceInCents(),
                car.getImageUrl(),
                engineDTO
        );


        return carDTO;
    }

    public void updateCar(Long id, CarRequest request) {
      Car car= carRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
      car.setModel(request.getModel());
      car.setYear(request.getYear());
      car.setDrivable(request.isDrivable());
      car.setPriceInCents(request.getPriceInCents());
      car.setImageUrl(request.getImageUrl());
      if(!Objects.equals(car.getEngine().getId(), request.getEngineID())){
          car.setEngine(engineService.findEngine(request.getEngineID()));
      }
      carRepository.save(car);

    }

    public void deleteCar(Long id ) {
        carRepository.deleteById(id);


    }

    public CarDTO findCarById(Long id) {
        Car car= carRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
        return mapCar(car);

    }

    private CarDTO mapCar(Car car) {
        return new CarDTO(car.getId(), car.getModel(), car.getYear(), car.isDrivable(),car.getPriceInCents(),car.getImageUrl(), new EngineDTO(car.getEngine().getId(), car.getEngine().getHorsePower(), car.getEngine().getCapacity()));
    }

    public Engine findEngine(Long Id) {
        return engineService.findEngine(Id);
    }


    private NotFoundException buildNotFoundException(Long id) {
        return new NotFoundException("car with id " + id + " not found");
    }

















}

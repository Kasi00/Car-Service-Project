package org.example.springbootdemo.cars.user;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.model.CarRequest;
import org.example.springbootdemo.cars.persistance.Car;
import org.example.springbootdemo.cars.persistance.CarRepository;
import org.example.springbootdemo.cars.user.model.UserRequest;
import org.example.springbootdemo.cars.user.persistance.AppUser;
import org.example.springbootdemo.cars.user.persistance.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final CarRepository carRepository;


    public AppUser setBalance(Long userId, Long newBalance) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        appUser.setBalanceInCents(newBalance); // Set the new balance
        return appUserRepository.save(appUser);
    }

    public void createUser(UserRequest userRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(userRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        appUser.setBalanceInCents(userRequest.getBalanceInCents());
        appUser.setRoles(userRequest.getRoleIds().stream()
                .map(roleService::getRole)
                .collect(Collectors.toSet()));

        appUserRepository.save(appUser);

    }

    public List<Car> getOwnedCars(Long userId) {
        AppUser appUser= appUserRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User not found"));
         return appUser.getOwnedCars();
    }





    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }


    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }


    public void addCarToUser(Long userId, Long id ) {
        AppUser appUser=appUserRepository.findById(userId).orElseThrow(()->new NotFoundException("User not found"));
        Car car =carRepository.findById(id).orElseThrow(()->new NotFoundException("Car not found"));

        if(appUser.getOwnedCars().contains(car)) {
            throw new RuntimeException("Car already owned by this USER <3");
        }

        appUser.getOwnedCars().add(car);
        car.getOwners().add(appUser);
        appUserRepository.save(appUser);
        carRepository.save(car);

    }

    public AppUser addBalanceToUser(Long userId, Long newBalance ) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setBalanceInCents(newBalance);
        return appUserRepository.save(user);
    }





}

package org.example.springbootdemo.cars.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.persistance.Car;
import org.example.springbootdemo.cars.user.model.UserRequest;
import org.example.springbootdemo.cars.user.persistance.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.springbootdemo.cars.security.AuthorizationConstants.ADMIN;
import static org.example.springbootdemo.cars.security.AuthorizationConstants.USER_OR_ADMIN;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize(ADMIN)
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}/owned-cars")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<List<Car>> getOwnedCars(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getOwnedCars(userId));
    }

    @PostMapping
    @PreAuthorize(ADMIN)
    public void createUser(@RequestBody @Valid UserRequest userRequest) {
        userService.createUser(userRequest);
    }



    @PostMapping("/{userId}/add-car/{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> addCarToUser(@PathVariable Long userId, @PathVariable Long id) {
        userService.addCarToUser(userId, id);
        return ResponseEntity.ok("Car added to user successfully!");
    }

    @PutMapping("/{userId}/balance")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> updateBalance(@PathVariable Long userId, @RequestParam Long newBalance) {
        userService.setBalance(userId, newBalance);
        return ResponseEntity.ok("Balance updated successfully!");
    }








}

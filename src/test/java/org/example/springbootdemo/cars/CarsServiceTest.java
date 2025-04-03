package org.example.springbootdemo.cars;

import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.persistance.Car;
import org.example.springbootdemo.cars.persistance.CarRepository;
import org.example.springbootdemo.cars.user.persistance.AppUser;
import org.example.springbootdemo.cars.user.persistance.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks  // This automatically injects the mocked repositories into CarsService
    private CarsService carsService;

    private AppUser user;
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setPriceInCents(10000L);

        // Set up mock user
        user = new AppUser();
        user.setId(1L);
        user.setBalanceInCents(15000L);
    }


    @Test
    void testBuyCar_UserHasEnoughBalance() {
        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));


        carsService.buyCar(1L, 1L);


        assertEquals(5000, user.getBalanceInCents());
        assertTrue(user.getOwnedCars().contains(car));
        assertTrue(car.getOwners().contains(user));

        verify(appUserRepository, times(1)).save(user);
        verify(carRepository, times(1)).save(car);
    }


    @Test
    void testBuyCar_UserDoesNotHaveEnoughBalance() {

        user.setBalanceInCents(5000L);

        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carsService.buyCar(1L, 1L));

        assertEquals("Sorry, you do not have enough balance to buy it ! ! !", exception.getMessage());
    }

    @Test
    void testBuyCar_UserNotFound() {
        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> carsService.buyCar(1L, 1L));
    }


    @Test
    void testBuyCar_CarNotFound() {
        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> carsService.buyCar(1L, 1L));
    }

    @Test
    void testSellCar_UserOwnsCar() {
        user.getOwnedCars().add(car);
        car.getOwners().add(user);

        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));

        carsService.sellCar(1L, 1L);

        assertFalse(user.getOwnedCars().contains(car));
        assertFalse(car.getOwners().contains(user));
        assertEquals(23000L, user.getBalanceInCents()); // Balance should increase by car price

        verify(appUserRepository, times(1)).save(user);
        verify(carRepository, times(1)).save(car);
    }


}




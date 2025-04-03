package org.example.springbootdemo.cars.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);

    }
}

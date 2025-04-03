package org.example.springbootdemo.cars.error;

public class InvalidLoginException  extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}

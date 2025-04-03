package org.example.springbootdemo.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.springbootdemo.cars.persistance.Engine;
@Data
@AllArgsConstructor
public class CarDTO {


    private Long id;
    private String model;
    private int year;
    private boolean drivable;
    private Long priceInCents;
    private String imageUrl;
    private EngineDTO engine;






    public void printDetails() {
        System.out.println("Model: " + model);
        System.out.println("EngineDTO info: ");
        engine.printInfo();

    }


}

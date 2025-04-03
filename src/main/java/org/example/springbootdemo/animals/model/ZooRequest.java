package org.example.springbootdemo.animals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZooRequest {
    private String name;
    private int amountAnimals;
    private Long animalId;


}

package org.example.springbootdemo.animals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZooDTO {
    private Long id;
    private int amountAnimals;
    private String name;
    private AnimalDTO animal;









    public void printDetails() {
        System.out.println("Name of zoo is : " + name);
        System.out.println("Amount of animals is : " + amountAnimals);
        System.out.println("Animal is : " + animal.getName());

    }
}

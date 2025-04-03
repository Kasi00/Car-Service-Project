package org.example.springbootdemo.animals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimalDTO {
    private Long id;
    private String name;
    private String type;






    public void printDetails() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);

    }

}

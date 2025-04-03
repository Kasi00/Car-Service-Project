package org.example.springbootdemo.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EngineDTO {
    private Long id;
    private int horsePower;
    private double capacity;



    public void printInfo(){
        System.out.println("EngineDTO HP is : " + horsePower);
        System.out.println("EngineDTO Capacity is : " + capacity);
    }


}

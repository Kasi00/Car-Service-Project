package org.example.springbootdemo.animals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimalRequest {
    private String name;
    private String type;


}

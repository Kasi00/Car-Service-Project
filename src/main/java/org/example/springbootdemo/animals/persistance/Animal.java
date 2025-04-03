package org.example.springbootdemo.animals.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="animal")
@SequenceGenerator(name="animal_seq_gen",sequenceName = "animal_seq",allocationSize = 1)
@Getter
@Setter
public class Animal {


    @Id
    @GeneratedValue(generator = "animal_seq_gen",strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name="name")
    private String name;


    @Column(name="type")
    private String type;






}

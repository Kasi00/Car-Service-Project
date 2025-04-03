package org.example.springbootdemo.cars.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="engine")
@SequenceGenerator(name="engine_seq_gen",sequenceName="engine_seq",allocationSize=1)
@Getter
@Setter
public class Engine {

    @Id
    @GeneratedValue(generator="engine_seq_gen",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="horse_power")
    private int horsePower;

    @Column(name="capacity")
    private double capacity;



}

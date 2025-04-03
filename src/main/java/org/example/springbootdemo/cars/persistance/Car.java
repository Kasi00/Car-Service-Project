package org.example.springbootdemo.cars.persistance;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.cars.user.persistance.AppUser;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="car")
@SequenceGenerator(name="car_seq_gen",sequenceName = "car_seq",allocationSize=1)
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(generator = "car_seq_gen",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="model")
    private String model;

    @Column(name="year")
    private int year;

    @Column(name="is_driveable")
    private boolean drivable;

    @Column(name="price_in_cents")
    private Long priceInCents=0L;

    @Column(name = "image_url")
    private String imageUrl;





    @ManyToOne
    @JoinColumn(name="engine_id")
    private Engine engine;


    @ManyToMany(mappedBy = "ownedCars")
    @JsonBackReference
    private List<AppUser> owners = new ArrayList<>();











}

package org.example.springbootdemo.cars.user.persistance;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.springbootdemo.cars.persistance.Car;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user", schema = "cars")
@SequenceGenerator(name = "app_user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue(generator = "app_user_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;


    @Column(name = "balance_in_cents")
    private Long balanceInCents = 0L;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();




    @ManyToMany
    @JoinTable(
            name = "user_car",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    @JsonManagedReference
    private List<Car> ownedCars = new ArrayList<>();









}

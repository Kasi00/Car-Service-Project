package org.example.springbootdemo.animals.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="zoo")
@SequenceGenerator(name="zoo_seq_gen",sequenceName = "zoo_seq",allocationSize = 1)
@Getter
@Setter
public class Zoo {

    @Id
    @GeneratedValue(generator = "zoo_seq_gen",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="amountAnimals")
    private Integer amountAnimals;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="animal_id")
    private Animal animal;


}

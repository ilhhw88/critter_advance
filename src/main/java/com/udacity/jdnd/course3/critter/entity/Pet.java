package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private PetType type;


    @ManyToOne
    private Customer owner;

    @Column
    private LocalDate birthDate;

    @Column(name = "notes", length = 500)
    private String notes;
}

package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    //@Column(name = "EmployeeIds")
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY)
    //@Column(name = "petIds")
    private List<Pet> pets;

    @Column
    private LocalDate date;

    @ElementCollection @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;
}

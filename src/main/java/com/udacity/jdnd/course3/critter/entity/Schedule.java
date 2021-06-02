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

    @ManyToMany(targetEntity = Schedule.class, mappedBy = "employee", fetch = FetchType.LAZY)
    //@Column(name = "EmployeeIds")
    private List<Long> employeeIds;

    @ManyToMany(targetEntity = Schedule.class, mappedBy = "pet", fetch = FetchType.LAZY)
    //@Column(name = "petIds")
    private List<Long> petIds;

    @Column
    private LocalDate date;

    @ElementCollection @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;
}

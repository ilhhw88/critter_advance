package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column
    private String employeeName;


    @ElementCollection @Enumerated(EnumType.STRING)
    private List<EmployeeSkill> skills;



    @ElementCollection @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysAvailable;

    
}

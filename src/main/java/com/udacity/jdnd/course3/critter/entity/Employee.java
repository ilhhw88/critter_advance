package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;

    @Column(name = "employeeName")
    private String employeeName;

    @Column
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<EmployeeSkill> skills;

    @Column
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysAvailable;

    public Employee(String employeeName, List<EmployeeSkill> skills, List<DayOfWeek> daysAvailable) {
        this.employeeName = employeeName;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }


    public Employee() {

    }
}

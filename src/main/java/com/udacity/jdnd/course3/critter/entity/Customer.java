package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CustomerId;

    @Column
    private String customerName;

    @Column
    private String phoneNumber;

    @Column(name = "notes", length = 500)
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

//    public Customer() {}
//
//    public Customer(String name, String phoneNumber, String notes) {
//        this.customerName = name;
//        this.phoneNumber = phoneNumber;
//        this.notes = notes;
//        this.pets = new ArrayList<>();
//    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet) {
        if (this.pets == null) {
            this.pets = new ArrayList<>();
        }
        this.pets.add(pet);
;
    }
}

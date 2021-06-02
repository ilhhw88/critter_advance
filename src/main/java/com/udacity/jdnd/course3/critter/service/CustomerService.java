package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getOwnerByPet(Long petId);

    //CustomerDTO getCustomerByCustomerId(Long customerId);


}

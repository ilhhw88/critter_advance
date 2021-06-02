package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = convertDTOToEntity(customerDTO);
        Customer customerForDB = customerRepository.save(customer);
        customerDTO.setId(customerForDB.getCustomerId());
        return customerDTO;
    }

    public Optional<Customer> getCustomerByCustomerId(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = convertEntityToDTO(customer);
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO getOwnerByPet(Long petId) {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            for (Pet pet : customer.getPets()) {
                if (pet.getId() == petId) {
                    return convertEntityToDTO(customer);
                }
            }
        }
        return new CustomerDTO();
    }

    //DB -> DTO
    private CustomerDTO convertEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerId());
        customerDTO.setName(customer.getCustomerName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        return customerDTO;
    }

    //DTO -> DB
    private Customer convertDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getId());
        customer.setCustomerName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return  customer;
    }
}

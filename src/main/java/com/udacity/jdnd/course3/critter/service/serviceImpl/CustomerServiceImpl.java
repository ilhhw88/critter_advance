package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetServiceImpl petService;
    @Autowired
    CustomerServiceImpl customerService;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO requestCustomerDTO) {

        Customer customer = convertDTOToEntity(requestCustomerDTO);
        Customer customerForDB = customerRepository.save(customer);
        CustomerDTO customerDTO = convertEntityToDTO(customerForDB);
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
        CustomerDTO customerDTO = new CustomerDTO();
        Optional<Pet> pets = petRepository.findById(petId);
        if (pets.isPresent()) {
            Pet pet = pets.get();
            Customer customer = pet.getOwner();
            //customerDTO = customer.map(this::convertEntityToDTO).orElseGet(CustomerDTO::new);
            customerDTO = convertEntityToDTO(customer);
            return customerDTO;
        }
        return customerDTO;
    }

    //DB -> DTO
    private CustomerDTO convertEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerId());
        customerDTO.setName(customer.getCustomerName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Pet> petList = petRepository.findAll();

        List<Long> petIds = new ArrayList<>();
        if (petList != null) {
            for (Pet pet : petList) {
                if (pet.getId() == customer.getCustomerId()) {
                    petIds.add(pet.getId());
                }
            }
            customerDTO.setPetIds(petIds);
            return customerDTO;
        }
        return customerDTO;
    }

    //DTO -> DB
    private Customer convertDTOToEntity(CustomerDTO customerDTO) {

        Customer customer = new Customer();

        customer.setCustomerName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return  customer;
    }
    private Pet convertPetDTOToDB(PetDTO petDTO) {
        Pet pet = new Pet();

        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        Optional<Customer> customerOptional = customerService.getCustomerByCustomerId(petDTO.getOwnerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pet.setOwner(customer);
            return pet;
        }
        return null;
    }
}

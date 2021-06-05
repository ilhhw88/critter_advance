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
//        List<Customer> customers = customerRepository.findAll();
//        for (Customer customer : customers) {
//            for (Long id : customer.getPets()) {
//                if (id.equals(petId)) {
//                    return convertEntityToDTO(customer);
//                }
//            }
//        }
        CustomerDTO customerDTO = new CustomerDTO();
        Optional<Pet> pets = petRepository.findById(petId);
        if (pets.isPresent()) {
            Pet pet = pets.get();
            Optional<Customer> customer = customerRepository.findById(pet.getId());
            customerDTO = customer.map(this::convertEntityToDTO).orElseGet(CustomerDTO::new);
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

        //customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

        List<Pet> PetList = customer.getPets();
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : PetList) {
            petIds.add(pet.getId());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    //DTO -> DB
    private Customer convertDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getId());
        customer.setCustomerName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            for (Long id : petIds) {
                pets.add(convertPetDTOToDB(petService.getPet(id)));
            }
            customer.setPets(pets);
        }
        return  customer;
    }
    private Pet convertPetDTOToDB(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setOwnerId(petDTO.getOwnerId());
        return pet;
    }
}

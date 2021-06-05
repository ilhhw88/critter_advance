package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetServiceImpl petService;

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = convertDTOToDB(petDTO);

        Pet petForDB = petRepository.save(pet);
        petDTO.setId(petForDB.getId());
        return petDTO;
    }

    @Override
    public PetDTO getPet(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.map(this::convertDBToDTO).orElse(new PetDTO());
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> pets = petRepository.findAll();
        for (Pet pet : pets) {
            petDTOList.add(convertDBToDTO(pet));
        }
        return petDTOList;
    }

    @Override
    public List<PetDTO> getPetsByOwner(Long ownerId) {
        Optional<Customer> customerOptional = customerRepository.findById(ownerId);
        List<PetDTO> petsDTO = new ArrayList<>();
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Pet> pets = customer.getPets();
            for (Pet pet: pets) {
                petsDTO.add(convertDBToDTO(pet));
            }
            return petsDTO;
        }
        return petsDTO;
        //return petsDTO.stream().map(this::convertDBToDTO).collect(Collectors.toList());
    }

    //DTO -> DB
    private Pet convertDTOToDB(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setOwnerId(petDTO.getOwnerId());
        return pet;
    }
    //DB -> DTO
    private PetDTO convertDBToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setOwnerId(pet.getOwnerId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());

        return petDTO;
    }
}

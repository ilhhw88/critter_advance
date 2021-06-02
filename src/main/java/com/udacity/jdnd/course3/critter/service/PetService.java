package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO savePet(PetDTO petDTO);

    PetDTO getPet(Long petId);

    List<PetDTO> getAllPets();

    List<PetDTO> getPetsByOwner(Long ownerId);
}

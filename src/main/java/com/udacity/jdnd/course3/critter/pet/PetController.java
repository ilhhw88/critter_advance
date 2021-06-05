package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.serviceImpl.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetRepository petRepository;
    @Autowired
    PetServiceImpl petService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetDTO petD = null;
        try {
            petD = petService.savePet(petDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petD;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO petD = null;
        try {
            petD = petService.getPet(petId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petD;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = new ArrayList<>();
        try {
            petDTOList = petService.getAllPets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> ownerPetsDTO = new ArrayList<>();
        try {
            ownerPetsDTO = petService.getPetsByOwner(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  ownerPetsDTO;
    }
}

package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    CustomerServiceImpl customerServiceImpl;

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToDB(scheduleDTO);
        Schedule scheduleForDB = scheduleRepository.save(schedule);
        scheduleDTO.setId(scheduleForDB.getId());

        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getAllSchedule() {
        List<ScheduleDTO> allSchedule = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            allSchedule.add(convertDBToDTO(schedule));
        }
        return allSchedule;
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        List<ScheduleDTO> petScheduleDTO = new ArrayList<>();
        List<Schedule> petSchedules = scheduleRepository.findAll();
        for (Schedule schedule : petSchedules) {
            if (schedule.getPetIds().equals(petId)) {
                petScheduleDTO.add((convertDBToDTO(schedule)));
            }
        }
        return petScheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        List<ScheduleDTO> employeeSchedules = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            if (schedule.getEmployeeIds().equals(employeeId)) {
                employeeSchedules.add(convertDBToDTO(schedule));
            }
        }
        return  employeeSchedules;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        Set<ScheduleDTO> customerSchedules = new HashSet<>();
        Optional<Customer> customerOptional = customerServiceImpl.getCustomerByCustomerId(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            for (Pet pet : customer.getPets()) {
                List<ScheduleDTO> scheduleDTOList = getScheduleForPet(pet.getId());
                customerSchedules.addAll(scheduleDTOList);
            }
        }
        return new ArrayList<>(customerSchedules);
    }

    //DB -> DTO
    private ScheduleDTO convertDBToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setPetIds(schedule.getPetIds());
        scheduleDTO.setEmployeeIds(schedule.getEmployeeIds());
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    //DTO -> DB
    private Schedule convertDTOToDB(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployeeIds(scheduleDTO.getEmployeeIds());
        schedule.setPetIds(scheduleDTO.getPetIds());
        schedule.setId(scheduleDTO.getId());

        return schedule;
    }
}

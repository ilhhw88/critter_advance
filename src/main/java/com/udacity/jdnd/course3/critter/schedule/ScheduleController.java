package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.serviceImpl.ScheduleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleImpl scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO scheduleD = null;
        try {
            scheduleD = scheduleService.createSchedule(scheduleDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleD;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        try {
            scheduleDTOList = scheduleService.getAllSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleListForPet = new ArrayList<>();
        try {
            scheduleListForPet = scheduleService.getScheduleForPet(petId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleListForPet;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> employeeSchedules = new ArrayList<>();
        try {
            employeeSchedules = scheduleService.getScheduleForEmployee(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeSchedules;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> customerSchedules = new ArrayList<>();
        try {
            customerSchedules = scheduleService.getScheduleForCustomer(customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerSchedules;
    }
}

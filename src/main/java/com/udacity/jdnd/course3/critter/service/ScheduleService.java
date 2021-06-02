package com.udacity.jdnd.course3.critter.service;



import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getAllSchedule();
    List<ScheduleDTO> getScheduleForPet(Long petId);
    List<ScheduleDTO> getScheduleForEmployee(Long employeeId);
    List<ScheduleDTO> getScheduleForCustomer(Long customerId);

}

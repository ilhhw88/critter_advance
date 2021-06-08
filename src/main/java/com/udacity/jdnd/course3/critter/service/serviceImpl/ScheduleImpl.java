package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.*;

@Transactional
@Service
public class ScheduleImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetServiceImpl petService;
    @Autowired
    EmployeeServiceImpl employeeService;
    @Autowired
    CustomerServiceImpl customerService;

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
        List<Schedule> scheduleList = scheduleRepository.findAll();
        for (Schedule schedule : scheduleList) {
            List<Pet> pets = schedule.getPets();
            if (pets != null) {
                for (Pet pet : pets) {
                    if (pet.getId().equals(petId)) {
                        petScheduleDTO.add(convertDBToDTO(schedule));
                    }
                }
            }
        }
        return petScheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        List<ScheduleDTO> employeeSchedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            List<Employee> employees = schedule.getEmployees();
            for (Employee employee : employees) {
                if (employee.getEmployeeId().equals(employeeId)) {
                    employeeSchedulesDTO.add(convertDBToDTO(schedule));
                }
            }
        }
        return  employeeSchedulesDTO;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        List<ScheduleDTO> customerSchedules = new ArrayList<>();
        Optional<Customer> customerOptional = customerService.getCustomerByCustomerId(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Pet> pets = customer.getPets();
            if (pets != null) {
                for (Pet pet : pets) {
                    List<ScheduleDTO> scheduleDTOList = getScheduleForPet(pet.getId());
                    customerSchedules.addAll(scheduleDTOList);
                }
            }
        }

        return customerSchedules;
    }

    //DB -> DTO
    private ScheduleDTO convertDBToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setActivities(schedule.getActivities());

        scheduleDTO.setDate(schedule.getDate());

        List<Pet> pets = schedule.getPets();
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : pets) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new ArrayList<>();
        List<Employee> employeeList = schedule.getEmployees();
        for (Employee employee : employeeList) {
            employeeIds.add(employee.getEmployeeId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        scheduleDTO.setId(schedule.getId());

        return scheduleDTO;
    }

    //DTO -> DB
    private Schedule convertDTOToDB(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        List<Employee> employeeList = new ArrayList<>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        for (Long employeeId : employeeIds) {
            employeeList.add(convertEmployeeDTOToDB(employeeService.getEmployee(employeeId)));
        }
        schedule.setEmployees(employeeList);

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds =scheduleDTO.getPetIds();
        for (Long petId : petIds) {
            pets.add(convertPetDTOToDB(petService.getPet(petId)));
        }
        schedule.setPets(pets);


        return schedule;
    }
    private Employee convertEmployeeDTOToDB(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getId());
        employee.setEmployeeName(employeeDTO.getName());
        List<EmployeeSkill> skillList = new ArrayList<>(employeeDTO.getSkills());
        employee.setSkills(skillList);
        List<DayOfWeek> dayOfWeekList = new ArrayList<>(employeeDTO.getDaysAvailable());
        employee.setDaysAvailable(dayOfWeekList);

        return employee;
    }

    private Pet convertPetDTOToDB(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        Optional<Customer> customerOptional = customerService.getCustomerByCustomerId(petDTO.getOwnerId());
        Customer customer = customerOptional.get();
        pet.setOwner(customer);
        return pet;
    }
}

package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        System.out.println("convert is not working!!!!");
        Employee employee = convertDTOToDB(employeeDTO);
        System.out.print("convert is working");
        Employee employeeForDB = employeeRepository.save(employee);
        employeeDTO.setId(employeeForDB.getEmployeeId());

        return employeeDTO;
    }

    @Override
    public EmployeeDTO getEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        return employeeOptional.map(this::convertDBToDTO).orElseGet(EmployeeDTO::new);
    }
    public List<EmployeeDTO> findEmployeeForService(DayOfWeek date, Set<EmployeeSkill> skills) {
        List<EmployeeDTO> employeeServiceList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee : employeeList) {
            if (employee.getDaysAvailable().contains(date) && employee.getSkills().contains(skills)) {
                employeeServiceList.add(convertDBToDTO(employee));
            }
        }
        return employeeServiceList;
    }

    //DB -> DTO
    private EmployeeDTO convertDBToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getEmployeeId());
        employeeDTO.setName(employee.getEmployeeName());

        //Set<EmployeeSkill> skillSet = new HashSet<>(employee.getSkills());

        employeeDTO.setSkills(new HashSet<>(employee.getSkills()));

        //Set<DayOfWeek> dayOfWeeks = new HashSet<>(employee.getDaysAvailable());
        employeeDTO.setDaysAvailable(new HashSet<>(employee.getDaysAvailable()));

        return employeeDTO;
    }

    //DTO -> DB
    private Employee convertDTOToDB(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getId());
        employee.setEmployeeName(employeeDTO.getName());
        //List<EmployeeSkill> skillList = new ArrayList<>(employeeDTO.getSkills());
        employee.setSkills(new ArrayList<>(employeeDTO.getSkills()));

        List<DayOfWeek> dayOfWeekList = new ArrayList<>(employeeDTO.getDaysAvailable());
        employee.setDaysAvailable(dayOfWeekList);

        return employee;
    }
}

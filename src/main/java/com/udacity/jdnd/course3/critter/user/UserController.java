package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.serviceImpl.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.serviceImpl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    EmployeeServiceImpl employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO custoDTO = null;
        try {
            custoDTO = customerService.saveCustomer(customerDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return custoDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = null;
        try {
            customerDTOList = customerService.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO customerDTO = null;
        try {
            customerDTO = customerService.getOwnerByPet(petId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO requestEmployeeDTO) {
        EmployeeDTO employeeD = null;
        //System.out.println(requestEmployeeDTO);
        try {
            employeeD = employeeService.saveEmployee(requestEmployeeDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("employee controller is working!!!");
        return employeeD;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeD = null;
        try {
            employeeD = employeeService.getEmployee(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeD;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getEmployee(employeeId);
        if (employeeDTO != null) {
            employeeDTO.setDaysAvailable(daysAvailable);
        }

    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate desiredDate = employeeDTO.getDate();
        DayOfWeek desiredDay = desiredDate.getDayOfWeek();
        Set<EmployeeSkill> desiredSkills = employeeDTO.getSkills();
        List<EmployeeDTO> employees = employeeService.findEmployeeForService(desiredDay, desiredSkills);
        return employees;
    }

}

package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployee(Long employeeId);


}

package com.example.onlinebankingapp.services.Employee;

import com.example.onlinebankingapp.entities.EmployeeEntity;
import com.example.onlinebankingapp.entities.Role;

import java.text.ParseException;
import java.util.List;

public interface IEmployeeService {
    List<EmployeeEntity> findAllEmployeesByRole(Role role) throws Exception;
    EmployeeEntity addEmployee(EmployeeEntity employeeEntity) throws ParseException;
}

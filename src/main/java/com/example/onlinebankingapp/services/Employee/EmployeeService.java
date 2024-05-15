package com.example.onlinebankingapp.services.Employee;

import com.example.onlinebankingapp.entities.EmployeeEntity;
import com.example.onlinebankingapp.entities.Role;
import com.example.onlinebankingapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class EmployeeService implements IEmployeeService{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> findAllEmployeesByRole(Role role) throws Exception {
        return employeeRepository.getAllByRole(role);
    }

    @Override
    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity) throws ParseException {
        return employeeRepository.save(employeeEntity);
    }
}

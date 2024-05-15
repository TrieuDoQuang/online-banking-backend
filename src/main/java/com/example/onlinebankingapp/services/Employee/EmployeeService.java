package com.example.onlinebankingapp.services.Employee;

import com.example.onlinebankingapp.dtos.EmployeeDTO;
import com.example.onlinebankingapp.entities.EmployeeEntity;
import com.example.onlinebankingapp.entities.Role;
import com.example.onlinebankingapp.repositories.EmployeeRepository;
import com.example.onlinebankingapp.utils.DataParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class EmployeeService implements IEmployeeService{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> findAllEmployeesByRole(Role role) throws Exception {
        return employeeRepository.getAllByRole(role);
    }

    @Override
    public EmployeeEntity addEmployee(EmployeeDTO employeeDTO) throws Exception {
        //Check email
        // ...
        //Check & harsh password
        // ...
        //Check citizenId
        // ...
        //Check name
        // ...
        // Check phone number
        if(!DataParserUtil.isValidPhonenumber(employeeDTO.getPhonenumber())){
            throw new Exception("Phonenumber is not in the correct format.");
        }
        //Check DoB
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        java.sql.Date formatDateOfBirth;
        java.util.Date parsedDate =  format.parse(employeeDTO.getDateOfBirth());
        formatDateOfBirth = new java.sql.Date(parsedDate.getTime());
        //Check Role
        Role parsedRole = Role.valueOf(employeeDTO.getRole());

        EmployeeEntity newEmployeeEntity = EmployeeEntity.builder()
                .address(employeeDTO.getAddress())
                .citizenId(employeeDTO.getCitizenid())
                .dateofbirth(formatDateOfBirth)
                .email(employeeDTO.getEmail())
                .name(employeeDTO.getName())
                .password(employeeDTO.getPassword())
                .phonenumber(employeeDTO.getPhonenumber())
                .role(parsedRole)
                .build();

        return employeeRepository.save(newEmployeeEntity);
    }
}

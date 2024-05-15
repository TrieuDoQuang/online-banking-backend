//package com.example.onlinebankingapp.controllers;
//
//import com.example.onlinebankingapp.dtos.EmployeeDTO;
//import com.example.onlinebankingapp.entities.EmployeeEntity;
//import com.example.onlinebankingapp.entities.enums.EmployeeRole;
//import com.example.onlinebankingapp.responses.ResponseObject;
//import com.example.onlinebankingapp.services.Employee.IEmployeeService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.validation.FieldError;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/employees")
//public class EmployeeController{
//    private IEmployeeService employeeService;
//
//    @Autowired
//    public EmployeeController(IEmployeeService employeeService){
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping("{role}")
//    public ResponseEntity<?> getEmployeesByRoles(@PathVariable("role") String role) {
//        try {
//            EmployeeRole employeeRole = EmployeeRole.valueOf(role);
//            List<EmployeeEntity> employeeEntityList = employeeService.findAllEmployeesByRole(employeeRole);
//            return ResponseEntity.ok(ResponseObject.builder()
//                    .data(employeeEntityList)
//                    .status(200)
//                    .message("Get Employees with role " + role + " successfully.")
//                    .build());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("")
//    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult result){
//        try {
//            if(result.hasErrors()) {
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//            EmployeeEntity employeeResponse = employeeService.addEmployee(employeeDTO);
//            return ResponseEntity.ok(ResponseObject.builder()
//                    .data(employeeResponse)
//                    .message("Create Employee successfully")
//                    .status(200)
//                    .build());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}

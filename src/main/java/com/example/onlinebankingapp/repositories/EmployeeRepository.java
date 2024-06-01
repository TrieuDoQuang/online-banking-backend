package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//not implemented
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}

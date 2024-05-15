package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.EmployeeEntity;
import com.example.onlinebankingapp.entities.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    List<EmployeeEntity> getAllByRole(EmployeeRole employeeRole);

    EmployeeEntity getEmployeeEntitiesByCitizenId(String citizenId);
}

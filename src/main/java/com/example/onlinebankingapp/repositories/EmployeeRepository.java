package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    List<EmployeeEntity> getAllByRole(String role);

    EmployeeEntity getEmployeeEntitiesByCitizenId(String citizenId);
}

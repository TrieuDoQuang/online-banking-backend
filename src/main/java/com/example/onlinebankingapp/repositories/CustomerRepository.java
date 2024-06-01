package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//in charge Trieu
//This repository interface defines methods for interacting with the CustomerEntity objects in the database.
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // Check if a customer exists by email and citizen ID
    boolean existsByEmailAndCitizenId(String email, String citizenId);
    // Check if a customer exists by email
    boolean existsByEmail(String email);
    // Check if a customer exists by citizen ID
    boolean existsByCitizenId(String citizenId);
    // Check if a customer exists by phone number
    boolean existsByPhoneNumber(String phoneNumber);

    // Find a customer by phone number
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
    // Find a customer by email
    Optional<CustomerEntity> findByEmail(String email);



}

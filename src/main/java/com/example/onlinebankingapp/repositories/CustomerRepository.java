package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByEmailAndCitizenId(String email, String citizenId);
    boolean existsByEmail(String email);
    boolean existsByCitizenId(String citizenId);
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
    Optional<CustomerEntity> findByEmail(String email);

}

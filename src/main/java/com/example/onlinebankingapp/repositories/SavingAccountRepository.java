package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepository extends JpaRepository<CustomerEntity, Long> {

}

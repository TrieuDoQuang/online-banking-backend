package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//in charge: khai
//This repository interface provides a method for retrieving a RewardEntity by its ID.
@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
    // Retrieve a reward entity by ID
    RewardEntity findRewardEntityById(Long id);
}

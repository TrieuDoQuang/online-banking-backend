package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
    RewardEntity findRewardEntityById(Long id);
}

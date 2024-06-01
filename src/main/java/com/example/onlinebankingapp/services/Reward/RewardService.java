package com.example.onlinebankingapp.services.Reward;

import com.example.onlinebankingapp.dtos.AccountRewardDTO;
import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.InvalidParamException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//interface defining abstract functions for Reward Service
//in charge: khai
public interface RewardService {
    // Insert a new reward based on the provided reward DTO
    RewardEntity insertReward(RewardDTO rewardDTO) throws InvalidParamException;

    // Retrieve a reward by its ID
    RewardEntity getRewardById(Long id) throws Exception;

    // Get all rewards
    List<RewardEntity> getAllRewards() throws Exception;

    // Update a reward based on the provided ID and reward DTO
    RewardEntity updateReward(Long id, RewardDTO rewardDTO) throws Exception;

    // Upload an image for a reward based on the provided ID and file
    RewardEntity uploadRewardImg(Long id, MultipartFile file) throws Exception;

    // Get rewards associated with a user based on the user ID
    List<AccountRewardEntity> getUserAccountRewards(Long userId) throws DataNotFoundException;

    // Redeem a reward for a user based on the provided account reward DTO
    AccountRewardEntity redeemReward(AccountRewardDTO accountRewardDTO) throws Exception;

    // Use a reward for a user based on the provided account reward DTO
    AccountRewardEntity useReward(AccountRewardDTO accountRewardDTO) throws Exception;
}

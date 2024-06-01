package com.example.onlinebankingapp.services.Reward;

import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.InvalidParamException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RewardService {
    RewardEntity insertReward(RewardDTO rewardDTO) throws InvalidParamException;

    RewardEntity getRewardById(Long id) throws Exception;

    List<RewardEntity> getAllRewards() throws Exception;

    RewardEntity updateReward(Long id, RewardDTO rewardDTO) throws Exception;

    RewardEntity uploadRewardImg(Long id, MultipartFile file) throws Exception;

    List<AccountRewardEntity> getUserAccountRewards(Long userId) throws DataNotFoundException;
}

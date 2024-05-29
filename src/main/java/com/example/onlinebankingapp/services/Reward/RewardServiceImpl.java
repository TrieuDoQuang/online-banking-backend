package com.example.onlinebankingapp.services.Reward;

import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.enums.RewardType;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.InvalidParamException;
import com.example.onlinebankingapp.repositories.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    @Override
    public RewardEntity insertReward(RewardDTO rewardDTO) throws InvalidParamException {
        String dataValidationResult = isRewardDTOValid(rewardDTO);
        if(!dataValidationResult.equals("OK")){
            throw new InvalidParamException(dataValidationResult);
        }

        RewardEntity newRewardEntity = RewardEntity.builder()
                .rewardName(rewardDTO.getRewardName())
                .rewardType(RewardType.valueOf(rewardDTO.getRewardType()))
                .costPoint(rewardDTO.getCostPoint())
                .build();

        return rewardRepository.save(newRewardEntity);
    }

    @Override
    public RewardEntity getRewardById(Long id) throws Exception {
        if(id == null){
            throw new Exception("Missing parameter");
        }

        RewardEntity queryRewardEntity = rewardRepository.findRewardEntityById(id);
        if(queryRewardEntity != null) {
            return queryRewardEntity;
        }
        throw new DataNotFoundException("Cannot find reward with Id: "+ id);
    }

    @Override
    public List<RewardEntity> getAllRewards() throws Exception {
        return rewardRepository.findAll();
    }

    @Override
    public RewardEntity updateReward(Long id, RewardDTO rewardDTO) throws Exception {
        String dataValidationResult = isRewardDTOValid(rewardDTO);
        if(!dataValidationResult.equals("OK")){
            throw new InvalidParamException(dataValidationResult);
        }

        if(id == null){
            throw new Exception("Missing parameter");
        }

        RewardEntity editedRewardEntity = rewardRepository.findRewardEntityById(id);

        editedRewardEntity.setRewardName(rewardDTO.getRewardName());
        editedRewardEntity.setCostPoint(rewardDTO.getCostPoint());
        editedRewardEntity.setRewardType(RewardType.valueOf(rewardDTO.getRewardType()));

        return rewardRepository.save(editedRewardEntity);
    }

    private String isRewardDTOValid(RewardDTO rewardDTO) {
        if (StringUtils.isBlank(rewardDTO.getRewardName())
                || StringUtils.isBlank(rewardDTO.getRewardType())) {
            return "Missing parameters";
        }

        try {
            RewardType rewardType = RewardType.valueOf(rewardDTO.getRewardType());
        } catch (IllegalArgumentException e){
            return "Invalid reward type";
        }

        if(rewardDTO.getCostPoint() <= 0 || rewardDTO.getCostPoint() > 9999){
            return "Reward cost must be between 1 and 9999";
        }

        return "OK";
    }
}

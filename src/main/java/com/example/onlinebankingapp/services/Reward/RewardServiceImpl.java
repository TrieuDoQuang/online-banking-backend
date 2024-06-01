package com.example.onlinebankingapp.services.Reward;

import com.example.onlinebankingapp.dtos.AccountRewardDTO;
import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.enums.RewardType;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.InvalidParamException;
import com.example.onlinebankingapp.repositories.AccountRewardRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import com.example.onlinebankingapp.repositories.RewardRepository;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import com.example.onlinebankingapp.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    // Dependency injections
    private final RewardRepository rewardRepository;
    private final PaymentAccountService paymentAccountService;
    private final AccountRewardRepository accountRewardRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    // Method to insert a new reward
    //in charge: khai
    @Override
    public RewardEntity insertReward(RewardDTO rewardDTO) throws InvalidParamException {
        // Validate the reward data
        String dataValidationResult = isRewardDTOValid(rewardDTO);
        if(!dataValidationResult.equals("OK")){
            throw new InvalidParamException(dataValidationResult);
        }

        // Create a new RewardEntity object from the DTO
        RewardEntity newRewardEntity = RewardEntity.builder()
                .rewardName(rewardDTO.getRewardName())
                .rewardType(RewardType.valueOf(rewardDTO.getRewardType()))
                .costPoint(rewardDTO.getCostPoint())
                .build();

        // Save and return the new reward entity
        return rewardRepository.save(newRewardEntity);
    }

    // Method to retrieve a reward by its ID
    //in charge: khai
    @Override
    public RewardEntity getRewardById(Long id) throws Exception {
        //check if id is null
        if(id == null){
            throw new Exception("Missing parameter");
        }

        //check if the query reward exists, yes then return the data, else throw error
        RewardEntity queryRewardEntity = rewardRepository.findRewardEntityById(id);
        if(queryRewardEntity != null) {
            return queryRewardEntity;
        }
        throw new DataNotFoundException("Cannot find reward with Id: "+ id);
    }

    // Method to get all rewards
    //in charge: khai
    @Override
    public List<RewardEntity> getAllRewards() throws Exception {
        return rewardRepository.findAll();
    }

    // Method to update an existing reward
    //in charge: khai
    @Override
    public RewardEntity updateReward(Long id, RewardDTO rewardDTO) throws Exception {
        // Validate the reward data
        String dataValidationResult = isRewardDTOValid(rewardDTO);
        if(!dataValidationResult.equals("OK")){
            throw new InvalidParamException(dataValidationResult);
        }

        if(id == null){
            throw new Exception("Missing parameter");
        }

        // Find the reward entity by ID and update its fields
        RewardEntity editedRewardEntity = rewardRepository.findRewardEntityById(id);

        editedRewardEntity.setRewardName(rewardDTO.getRewardName());
        editedRewardEntity.setCostPoint(rewardDTO.getCostPoint());
        editedRewardEntity.setRewardType(RewardType.valueOf(rewardDTO.getRewardType()));

        // Save and return the updated reward entity
        return rewardRepository.save(editedRewardEntity);
    }

    // Method to upload an image for a reward
    //in charge: khai
    @Override
    public RewardEntity uploadRewardImg(Long id, MultipartFile file) throws Exception {
        //find the reward, check if the reward exists
        RewardEntity editedRewardEntity = rewardRepository.findRewardEntityById(id);
        if(editedRewardEntity == null){
            throw new DataNotFoundException("Cannot find reward with Id: "+ id);
        }

        //validate image
        if(!ImageUtils.isImgValid(file)){
            throw new InvalidParamException("Image must be less than 2mbs and in the following formats: jpeg, jpg, png, webp");
        }

        editedRewardEntity.setImage(file.getBytes());

        //save the new reward with the image data
        return rewardRepository.save(editedRewardEntity);
    }

    // Method to get rewards for a user's account
    //in charge: khai
    @Override
    public List<AccountRewardEntity> getUserAccountRewards(Long userId) throws DataNotFoundException {
        //get all payment accounts of the user
        List<PaymentAccountEntity> userPaymentAccountsList = paymentAccountService.getPaymentAccountsByCustomerId(userId);

        //find all the account rewards in each payment accounts
        List<AccountRewardEntity> userAccountRewardsList = new ArrayList<>();
        for (PaymentAccountEntity paymentAccount : userPaymentAccountsList) {
            userAccountRewardsList.addAll(accountRewardRepository.findAccountRewardEntitiesByAccountRewardKeyPaymentAccount(paymentAccount));
        }

        //return result
        return userAccountRewardsList;
    }

    // Method to redeem a reward
    //in charge: khai
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Override
    public AccountRewardEntity redeemReward(AccountRewardDTO accountRewardDTO) throws Exception {
        PaymentAccountEntity queryPaymentAccount = paymentAccountService.getPaymentAccountById(accountRewardDTO.getPaymentAccountId());
        RewardEntity queryReward = getRewardById(accountRewardDTO.getRewardId());

        // Check if the user has enough points to redeem the reward
        if(queryPaymentAccount.getRewardPoint() < queryReward.getCostPoint()){
            throw new Exception("Insufficient point, the required point to redeem is " + queryReward.getCostPoint().toString());
        }

        // Create a relationship key for the account-reward relationship
        AccountRewardEntity.AccountRewardRelationshipKey relationshipKey = AccountRewardEntity.AccountRewardRelationshipKey.builder()
                .reward(queryReward)
                .paymentAccount(queryPaymentAccount)
                .build();

        //check if this voucher has been redeemed by this account
        if(accountRewardRepository.existsAccountRewardEntityByAccountRewardKey(relationshipKey)){
            throw new Exception("This account has already redeemed this reward");
        }

        // Create a new AccountRewardEntity
        AccountRewardEntity newAccountReward = AccountRewardEntity.builder()
                .accountRewardKey(relationshipKey)
                .build();

        //subtract point from payment account
        queryPaymentAccount.setRewardPoint(queryPaymentAccount.getRewardPoint()- queryReward.getCostPoint());
        paymentAccountRepository.save(queryPaymentAccount);
        return accountRewardRepository.save(newAccountReward);
    }

    // Method to use a reward
    //in charge: khai
    @Override
    public AccountRewardEntity useReward(AccountRewardDTO accountRewardDTO) throws Exception {
        //find the payment account and reward
        PaymentAccountEntity queryPaymentAccount = paymentAccountService.getPaymentAccountById(accountRewardDTO.getPaymentAccountId());
        RewardEntity queryReward = getRewardById(accountRewardDTO.getRewardId());

        AccountRewardEntity.AccountRewardRelationshipKey relationshipKey = AccountRewardEntity.AccountRewardRelationshipKey.builder()
                .reward(queryReward)
                .paymentAccount(queryPaymentAccount)
                .build();

        //find the account with this reward
        AccountRewardEntity accountReward = accountRewardRepository.findAccountRewardEntityByAccountRewardKey(relationshipKey);

        //check if the account has this reward
        if(accountReward == null){
            throw new Exception("This account doesn't have reward Id: " + accountRewardDTO.getRewardId());
        }

        //check if the reward is still valid
        if(accountReward.isValid() == false){
            throw new Exception("This reward has already been used");
        }

        accountReward.setValid(false);

        return accountRewardRepository.save(accountReward);
    }

    // Method to validate a RewardDTO
    //in charge: khai
    private String isRewardDTOValid(RewardDTO rewardDTO) {
        //check if name or type is blank
        if (StringUtils.isBlank(rewardDTO.getRewardName())
                || StringUtils.isBlank(rewardDTO.getRewardType())) {
            return "Missing parameters";
        }

        //check if type is a valid enum data
        try {
            RewardType rewardType = RewardType.valueOf(rewardDTO.getRewardType());
        } catch (IllegalArgumentException e){
            return "Invalid reward type";
        }

        //check if reward cost is between 0 & 9999
        if(rewardDTO.getCostPoint() <= 0 || rewardDTO.getCostPoint() > 9999){
            return "Reward cost must be between 1 and 9999";
        }

        //return ok when all tests passed
        return "OK";
    }
}

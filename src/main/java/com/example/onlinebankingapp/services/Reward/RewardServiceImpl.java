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
    private final RewardRepository rewardRepository;
    private final PaymentAccountService paymentAccountService;
    private final AccountRewardRepository accountRewardRepository;
    private final PaymentAccountRepository paymentAccountRepository;
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

    @Override
    public RewardEntity uploadRewardImg(Long id, MultipartFile file) throws Exception {
        RewardEntity editedRewardEntity = rewardRepository.findRewardEntityById(id);
        if(editedRewardEntity == null){
            throw new DataNotFoundException("Cannot find reward with Id: "+ id);
        }

        if(!ImageUtils.isImgValid(file)){
            throw new InvalidParamException("Image must be less than 2mbs and in the following formats: jpeg, jpg, png, webp");
        }

        editedRewardEntity.setImage(file.getBytes());
        return rewardRepository.save(editedRewardEntity);
    }

    @Override
    public List<AccountRewardEntity> getUserAccountRewards(Long userId) throws DataNotFoundException {
        List<PaymentAccountEntity> userPaymentAccountsList = paymentAccountService.getPaymentAccountsByCustomerId(userId);
        List<AccountRewardEntity> userAccountRewardsList = new ArrayList<>();
        for (PaymentAccountEntity paymentAccount : userPaymentAccountsList) {
//            List<AccountRewardEntity> accountRewardList
//                    = accountRewardRepository.findAccountRewardEntitiesByAccountRewardKeyPaymentAccount(paymentAccount);
//            List<AccountRewardEntity.AccountRewardRelationshipKey> rewardRelationshipKeys = accountRewardList.stream().map(AccountRewardEntity::getAccountRewardKey).toList();
//            List<RewardEntity> rewardsOfPaymentAccount = rewardRelationshipKeys.stream().map(AccountRewardEntity.AccountRewardRelationshipKey::getReward).toList();
//            userRewardsList.addAll(rewardsOfPaymentAccount);
            userAccountRewardsList.addAll(accountRewardRepository.findAccountRewardEntitiesByAccountRewardKeyPaymentAccount(paymentAccount));
        }

        return userAccountRewardsList;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Override
    public AccountRewardEntity redeemReward(AccountRewardDTO accountRewardDTO) throws Exception {
        PaymentAccountEntity queryPaymentAccount = paymentAccountService.getPaymentAccountById(accountRewardDTO.getPaymentAccountId());
        RewardEntity queryReward = getRewardById(accountRewardDTO.getRewardId());

        //check if point is enough
        if(queryPaymentAccount.getRewardPoint() < queryReward.getCostPoint()){
            throw new Exception("Insufficient point, the required point to redeem is " + queryReward.getCostPoint().toString());
        }

        AccountRewardEntity.AccountRewardRelationshipKey relationshipKey = AccountRewardEntity.AccountRewardRelationshipKey.builder()
                .reward(queryReward)
                .paymentAccount(queryPaymentAccount)
                .build();

        //check if this voucher has been redeemed by this account
        if(accountRewardRepository.existsAccountRewardEntityByAccountRewardKey(relationshipKey)){
            throw new Exception("This account has already redeemed this reward");
        }

        AccountRewardEntity newAccountReward = AccountRewardEntity.builder()
                .accountRewardKey(relationshipKey)
                .build();

        //subtract point from payment account
        queryPaymentAccount.setRewardPoint(queryPaymentAccount.getRewardPoint()- queryReward.getCostPoint());
        paymentAccountRepository.save(queryPaymentAccount);
        return accountRewardRepository.save(newAccountReward);
    }

    @Override
    public AccountRewardEntity useReward(AccountRewardDTO accountRewardDTO) throws Exception {
        PaymentAccountEntity queryPaymentAccount = paymentAccountService.getPaymentAccountById(accountRewardDTO.getPaymentAccountId());
        RewardEntity queryReward = getRewardById(accountRewardDTO.getRewardId());

        AccountRewardEntity.AccountRewardRelationshipKey relationshipKey = AccountRewardEntity.AccountRewardRelationshipKey.builder()
                .reward(queryReward)
                .paymentAccount(queryPaymentAccount)
                .build();

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

package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.Reward.RewardListResponse;
import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountResponse;
import com.example.onlinebankingapp.services.Reward.RewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @PostMapping("")
    public ResponseEntity<?> insertReward(@Valid @RequestBody RewardDTO rewardDTO) {
        try {
            RewardEntity newRewardEntity = rewardService.insertReward(rewardDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Create reward successfully!")
                    .result(RewardResponse.fromReward(newRewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRewards() {
        try {
            List<RewardEntity> rewardEntityList = rewardService.getAllRewards();
            List<RewardResponse> rewardResponseList = rewardEntityList.stream()
                    .map(RewardResponse::fromReward)
                    .toList();

            RewardListResponse rewardListResponse = RewardListResponse.builder()
                    .rewards(rewardResponseList)
                    .build();

            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get reward list successfully!")
                    .result(rewardListResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRewardById(@Valid @PathVariable("id") Long id){
        try {

            RewardEntity rewardEntity = rewardService.getRewardById(id);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get reward successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

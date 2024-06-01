package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.RewardDTO;
import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.responses.AccountReward.AccountRewardListResponse;
import com.example.onlinebankingapp.responses.AccountReward.AccountRewardResponse;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.Reward.RewardListResponse;
import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountResponse;
import com.example.onlinebankingapp.services.Reward.RewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
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
    public ResponseEntity<?> getRewardById(@Valid @PathVariable("id") Long id) {
        try {

            RewardEntity rewardEntity = rewardService.getRewardById(id);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get reward successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> retrieve(@Valid @PathVariable("id") Long id) throws Exception {
        try {
            RewardEntity rewardEntity = rewardService.getRewardById(id);
            ByteArrayResource body;
            try {
                body = new ByteArrayResource(rewardEntity.getImage());
            } catch (Exception e) {
                throw new Exception("This reward has no image");
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)).cachePrivate().mustRevalidate())
                    .body(body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<?> uploadRewardImg(@Valid @PathVariable("id") Long id, @RequestBody MultipartFile file) {
        try {

            RewardEntity rewardEntity = rewardService.uploadRewardImg(id, file);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Upload reward's image successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReward(@Valid @PathVariable("id") Long id, @Valid @RequestBody RewardDTO rewardDTO) {
        try {

            RewardEntity rewardEntity = rewardService.updateReward(id, rewardDTO);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Update reward successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/userReward/{id}")
    public ResponseEntity<?> getUserRewards(@Valid @PathVariable("id") Long userId) {
        try {
            List<AccountRewardEntity> accountRewardList = rewardService.getUserAccountRewards(userId);
            List<AccountRewardResponse> accountRewardResponseList = accountRewardList.stream()
                    .map(AccountRewardResponse::fromAccountReward)
                    .toList();

            AccountRewardListResponse accountRewardListResponse = AccountRewardListResponse.builder()
                    .accountRewards(accountRewardResponseList)
                    .build();

            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get user reward list successfully!")
                    .result(accountRewardListResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

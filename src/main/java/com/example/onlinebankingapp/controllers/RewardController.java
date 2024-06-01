package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.AccountRewardDTO;
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

    // Endpoint for inserting a reward
    // In charge: Khai
    @PostMapping("")
    public ResponseEntity<?> insertReward(@Valid @RequestBody RewardDTO rewardDTO) {
        try {
            //call service layer to insert a reward
            RewardEntity newRewardEntity = rewardService.insertReward(rewardDTO);

            //return response
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Create reward successfully!")
                    .result(RewardResponse.fromReward(newRewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for getting all rewards
    // In charge: Khai
    @GetMapping("")
    public ResponseEntity<?> getAllRewards() {
        try {
            // Retrieve a list of reward entities
            List<RewardEntity> rewardEntityList = rewardService.getAllRewards();

            //create response
            List<RewardResponse> rewardResponseList = rewardEntityList.stream()
                    .map(RewardResponse::fromReward)
                    .toList();

            RewardListResponse rewardListResponse = RewardListResponse.builder()
                    .rewards(rewardResponseList)
                    .build();

            // Return the response
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get reward list successfully!")
                    .result(rewardListResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for getting a reward by id
    // In charge: Khai
    @GetMapping("/{id}")
    public ResponseEntity<?> getRewardById(@Valid @PathVariable("id") Long id) {
        try {
            // Retrieve a reward entity by its ID
            RewardEntity rewardEntity = rewardService.getRewardById(id);

            // return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get reward successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for getting an image data of a reward
    // In charge: Khai
    @GetMapping("/image/{id}")
    public ResponseEntity<?> retrieve(@Valid @PathVariable("id") Long id) throws Exception {
        try {
            // Retrieve a reward entity by its ID
            RewardEntity rewardEntity = rewardService.getRewardById(id);
            // Create image data for response body
            ByteArrayResource body;
            try {
                body = new ByteArrayResource(rewardEntity.getImage());
            } catch (Exception e) {
                throw new Exception("This reward has no image");
            }

            // return response with image data
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)).cachePrivate().mustRevalidate())
                    .body(body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for uploading an image data of a reward
    // In charge: Khai
    @PutMapping("/image/{id}")
    public ResponseEntity<?> uploadRewardImg(@Valid @PathVariable("id") Long id, @RequestBody MultipartFile file) {
        try {
            // Upload the reward's image
            RewardEntity rewardEntity = rewardService.uploadRewardImg(id, file);

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Upload reward's image successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for uploading a reward information
    // In charge: Khai
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReward(@Valid @PathVariable("id") Long id, @Valid @RequestBody RewardDTO rewardDTO) {
        try {
            // Update the reward details
            RewardEntity rewardEntity = rewardService.updateReward(id, rewardDTO);

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Update reward successfully")
                    .status(HttpStatus.OK)
                    .result(RewardResponse.fromReward(rewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for getting all rewards of a user
    // In charge: Khai
    @GetMapping("/userReward/{id}")
    public ResponseEntity<?> getUserRewards(@Valid @PathVariable("id") Long userId) {
        try {
            // Retrieve a list of account rewards for the specified user
            List<AccountRewardEntity> accountRewardList = rewardService.getUserAccountRewards(userId);

            //Build response
            List<AccountRewardResponse> accountRewardResponseList = accountRewardList.stream()
                    .map(AccountRewardResponse::fromAccountReward)
                    .toList();

            AccountRewardListResponse accountRewardListResponse = AccountRewardListResponse.builder()
                    .accountRewards(accountRewardResponseList)
                    .build();

            //return response
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get user reward list successfully!")
                    .result(accountRewardListResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for redeeming a reward
    // In charge: Khai
    @PostMapping("/userReward/redeem")
    public ResponseEntity<?> redeemReward(@Valid @RequestBody AccountRewardDTO accountRewardDTO) {
        try {
            //redeem the requested reward
            AccountRewardEntity newAccountRewardEntity = rewardService.redeemReward(accountRewardDTO);

            //return the result in response
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Redeem reward successfully!")
                    .result(AccountRewardResponse.fromAccountReward(newAccountRewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for using a reward
    // In charge: Khai
    @PutMapping("/userReward/useReward")
    public ResponseEntity<?> useReward(@Valid @RequestBody AccountRewardDTO accountRewardDTO) {
        try {
            //use the requested reward
            AccountRewardEntity newAccountRewardEntity = rewardService.useReward(accountRewardDTO);

            //return the result in response
            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Use reward successfully!")
                    .result(AccountRewardResponse.fromAccountReward(newAccountRewardEntity))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

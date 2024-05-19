package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.responses.InterestRate.InterestRateResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.InterestRate.InterestRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interestRates")
@RequiredArgsConstructor
public class InterestRateController {
    private final InterestRateService interestRateService;

    @PostMapping("")
    public ResponseEntity<?> insertInterestRate(
            @Valid @RequestBody InterestRateDTO interestRateDTO
    ) {
        try {
            InterestRateEntity interestRateResponse = interestRateService.insertInterestRate(interestRateDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Thêm phương thức lãi xuất thành công!")
                            .result(InterestRateResponse.fromInterestRate(interestRateResponse))
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

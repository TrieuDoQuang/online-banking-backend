package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.responses.InterestRate.InterestRateListResponse;
import com.example.onlinebankingapp.responses.InterestRate.InterestRateResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.InterestRate.InterestRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<?> getAllInterestRates(){
        try{
            List<InterestRateEntity> interestRateEntities = interestRateService.getAllInterestRates();

            List<InterestRateResponse> interestRateResponses = interestRateEntities.stream()
                    .map(InterestRateResponse::fromInterestRate)
                    .toList();

            InterestRateListResponse interestRateListResponse = InterestRateListResponse.builder()
                    .interestRateResponses(interestRateResponses)
                    .build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Lấy tất cả phương thức lãi xuất thành công!")
                            .result(interestRateListResponse)
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getInterestRateById(@Valid @PathVariable("id") Long interestRate_Id){
        try{
            InterestRateEntity queryInterestRate = interestRateService.getInterestRateById(interestRate_Id);
            return ResponseEntity.ok().body(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Lấy phương thức lãi xuất thành công!")
                            .result(InterestRateResponse.fromInterestRate(queryInterestRate))
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

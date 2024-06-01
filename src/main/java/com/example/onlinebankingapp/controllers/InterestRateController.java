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

    //end point for inserting an interest rate
    //in charge: khai
    @PostMapping("")
    public ResponseEntity<?> insertInterestRate(
            @Valid @RequestBody InterestRateDTO interestRateDTO
    ) {
        try {
            // Insert the interest rate using the provided DTO
            InterestRateEntity interestRateResponse = interestRateService.insertInterestRate(interestRateDTO);

            // Return a successful response with the inserted interest rate data
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

    //end point for getting all the interest rates
    //in charge: khai
    @GetMapping("")
    public ResponseEntity<?> getAllInterestRates(){
        try{
            // Retrieve all interest rates
            List<InterestRateEntity> interestRateEntities = interestRateService.getAllInterestRates();

            //build response
            List<InterestRateResponse> interestRateResponses = interestRateEntities.stream()
                    .map(InterestRateResponse::fromInterestRate)
                    .toList();

            InterestRateListResponse interestRateListResponse = InterestRateListResponse.builder()
                    .interestRateResponses(interestRateResponses)
                    .build();

            // Return a successful response with the interest rate data
            return ResponseEntity.ok().body(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Lấy tất cả phương thức lãi xuất thành công!")
                            .result(interestRateListResponse)
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting an interest rate by id
    //in charge: khai
    @GetMapping("{id}")
    public ResponseEntity<?> getInterestRateById(@Valid @PathVariable("id") Long interestRate_Id){
        try{
            // Retrieve the interest rate by ID
            InterestRateEntity queryInterestRate = interestRateService.getInterestRateById(interestRate_Id);

            // Return a successful response with the interest rate data
            return ResponseEntity.ok().body(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Lấy phương thức lãi xuất thành công!")
                            .result(InterestRateResponse.fromInterestRate(queryInterestRate))
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for updating an interest rate
    //in charge: khai
    @PutMapping("{id}")
    public ResponseEntity<?> updateInterestRate(
            @Valid @PathVariable("id") Long interestRate_Id,
            @Valid @RequestBody InterestRateDTO interestRateDTO) {
        try {
            //updating the interest rate
            InterestRateEntity updatedInterestRate = interestRateService.updateInterestRate(interestRate_Id, interestRateDTO);

            //return result in response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Cập nhật phương thức lãi xuất thành công!")
                    .result(InterestRateResponse.fromInterestRate(updatedInterestRate))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

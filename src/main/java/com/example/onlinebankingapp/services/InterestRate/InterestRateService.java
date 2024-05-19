package com.example.onlinebankingapp.services.InterestRate;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

public interface InterestRateService {
    InterestRateEntity insertInterestRate(InterestRateDTO interestRateDTO) throws DataNotFoundException;

    List<InterestRateEntity> getAllInterestRates() throws Exception;

    InterestRateEntity getInterestRateById(Long id) throws Exception;

    InterestRateEntity updateInterestRate(Long id, InterestRateDTO interestRateDTO) throws  Exception;
}

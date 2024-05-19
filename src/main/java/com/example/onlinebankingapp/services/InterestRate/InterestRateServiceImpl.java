package com.example.onlinebankingapp.services.InterestRate;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.InterestRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService{
    private final InterestRateRepository interestRateRepository;
    @Override
    public InterestRateEntity insertInterestRate(InterestRateDTO interestRateDTO) throws DataNotFoundException {
        //data validation
        String validationResult = isInterestRateDTOValid(interestRateDTO);
        if(!validationResult.equals("OK")){
            throw new DataIntegrityViolationException(validationResult);
        }

        Integer term = interestRateDTO.getTerm();
        Double rate = interestRateDTO.getInterestRate();

        InterestRateEntity newInterestRate = InterestRateEntity.builder()
                .term(term)
                .interestRate(rate)
                .build();

        return interestRateRepository.save(newInterestRate);
    }

    @Override
    public List<InterestRateEntity> getAllInterestRates() throws Exception {
        return interestRateRepository.findAll();
    }

    @Override
    public InterestRateEntity getInterestRateById(Long id) throws Exception {
        InterestRateEntity queryInterestRate = interestRateRepository.findInterestRateEntityById(id);
        if(queryInterestRate == null){
            throw new DataNotFoundException("Phương thức lãi xuất không tồn tại");
        }
        return queryInterestRate;
    }

    @Override
    public InterestRateEntity updateInterestRate(Long id, InterestRateDTO interestRateDTO) throws Exception {
        InterestRateEntity updatedInterestRateEntity = getInterestRateById(id);

        //data validation
        String validationResult = isInterestRateDTOValid(interestRateDTO);
        if(!validationResult.equals("OK")){
            throw new DataIntegrityViolationException(validationResult);
        }

        updatedInterestRateEntity.setInterestRate(interestRateDTO.getInterestRate());
        updatedInterestRateEntity.setTerm(interestRateDTO.getTerm());

        return interestRateRepository.save(updatedInterestRateEntity);
    }

    private String isInterestRateDTOValid(InterestRateDTO interestRateDTO){
        Integer term = interestRateDTO.getTerm();
        Double rate = interestRateDTO.getInterestRate();

        if(term < 1 || term > 99){
            return "Số kì phải lớn hơn 1 và bé hơn 99 tháng";
        }

        if(rate <= 0 || rate > 999){
            return "Lãi suất phải lớn hơn 0% và bé hơn 999%";
        }

        if(interestRateRepository.existsByTermEqualsAndInterestRateEquals(term, rate)){
            return "Phương thức lãi xuất này đã tồn tại";
        }

        return "OK";
    }
}
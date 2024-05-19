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
        //minimum term 1 month, maximum term 99 months
        Integer term = interestRateDTO.getTerm();
        if(term < 1 || term > 99){
            throw new DataIntegrityViolationException("Số kì phải lớn hơn 1 và bé hơn 99 tháng");
        }

        //min interest > 0, max < 999
        Double rate = interestRateDTO.getInterestRate();
        if(rate <= 0 || rate > 999){
            throw new DataIntegrityViolationException("Lãi suất phải lớn hơn 0% và bé hơn 999%");
        }

        if(interestRateRepository.existsByTermEqualsAndInterestRateEquals(term, rate)){
            throw new DataIntegrityViolationException("Phương thức lãi xuất này đã tồn tại");
        }

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
        return null;
    }
}

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

        InterestRateEntity newInterestRate = InterestRateEntity.builder()
                .term(interestRateDTO.getTerm())
                .interestRate(interestRateDTO.getInterestRate())
                .minBalance(interestRateDTO.getMinBalance())
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
        updatedInterestRateEntity.setMinBalance(interestRateDTO.getMinBalance());

        return interestRateRepository.save(updatedInterestRateEntity);
    }

    private String isInterestRateDTOValid(InterestRateDTO interestRateDTO){
        Integer term = interestRateDTO.getTerm();
        Double rate = interestRateDTO.getInterestRate();
        Double minBalance = interestRateDTO.getMinBalance();

        if(term < 1 || term > 99){
            return "Số kì phải lớn hơn 1 và bé hơn 99 tháng";
        }

        if(rate <= 0 || rate > 999){
            return "Lãi suất phải lớn hơn 0% và bé hơn 999%";
        }

        if(minBalance <= 100000 || minBalance > 999999999){
            return "Số tiền gửi tối thiểu phải lớn hơn 100.000 và bé hơn 999.999.999";
        }

        if(interestRateRepository.existsByTermEqualsAndInterestRateEqualsAndMinBalanceEquals(term, rate, minBalance)){
            return "Phương thức lãi xuất này đã tồn tại";
        }

        return "OK";
    }
}
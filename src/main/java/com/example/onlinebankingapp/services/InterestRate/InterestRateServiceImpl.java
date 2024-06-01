package com.example.onlinebankingapp.services.InterestRate;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.InterestRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing interest rates.
 * This class provides methods to insert, update, and retrieve interest rate entities.
 * It uses the InterestRateRepository to perform CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService{
    private final InterestRateRepository interestRateRepository;

    /**
     * Inserts a new interest rate into the system.
     * Validates the input data before saving.
     *
     * @param interestRateDTO Data Transfer Object containing interest rate details.
     * @return InterestRateEntity The newly created interest rate entity.
     * @throws DataNotFoundException if validation fails or any required data is not found.
     */
    //in charge: khai
    @Override
    public InterestRateEntity insertInterestRate(InterestRateDTO interestRateDTO) throws DataNotFoundException {
        // Validate input data
        String validationResult = isInterestRateDTOValid(interestRateDTO);
        if(!validationResult.equals("OK")){
            throw new DataIntegrityViolationException(validationResult);
        }

        // Create a new InterestRateEntity
        InterestRateEntity newInterestRate = InterestRateEntity.builder()
                .term(interestRateDTO.getTerm())
                .interestRate(interestRateDTO.getInterestRate())
                .minBalance(interestRateDTO.getMinBalance())
                .build();

        // Save and return the new interest rate entity
        return interestRateRepository.save(newInterestRate);
    }

    /**
     * Retrieves all interest rates from the system.
     *
     * @return List<InterestRateEntity> A list of all interest rate entities.
     * @throws Exception if an error occurs while retrieving the data.
     */
    //in charge: khai
    @Override
    public List<InterestRateEntity> getAllInterestRates() throws Exception {
        return interestRateRepository.findAll();
    }

    /**
     * Retrieves an interest rate by its ID.
     *
     * @param id The ID of the interest rate to be retrieved.
     * @return InterestRateEntity The interest rate entity corresponding to the specified ID.
     * @throws Exception if an error occurs while retrieving the data.
     */
    //in charge: khai
    @Override
    public InterestRateEntity getInterestRateById(Long id) throws Exception {
        InterestRateEntity queryInterestRate = interestRateRepository.findInterestRateEntityById(id);
        if(queryInterestRate == null){
            throw new DataNotFoundException("Phương thức lãi xuất không tồn tại");
        }
        return queryInterestRate;
    }

    /**
     * Updates an existing interest rate.
     * Validates the input data before updating.
     *
     * @param id The ID of the interest rate to be updated.
     * @param interestRateDTO Data Transfer Object containing updated interest rate details.
     * @return InterestRateEntity The updated interest rate entity.
     * @throws Exception if an error occurs while updating the data.
     */
    //in charge: khai
    @Override
    public InterestRateEntity updateInterestRate(Long id, InterestRateDTO interestRateDTO) throws Exception {
        InterestRateEntity updatedInterestRateEntity = getInterestRateById(id);

        // Validate input data
        String validationResult = isInterestRateDTOValid(interestRateDTO);
        if(!validationResult.equals("OK")){
            throw new DataIntegrityViolationException(validationResult);
        }

        // Update the interest rate entity
        updatedInterestRateEntity.setInterestRate(interestRateDTO.getInterestRate());
        updatedInterestRateEntity.setTerm(interestRateDTO.getTerm());
        updatedInterestRateEntity.setMinBalance(interestRateDTO.getMinBalance());

        // Save and return the updated interest rate entity
        return interestRateRepository.save(updatedInterestRateEntity);
    }

    /**
     * Validates the InterestRateDTO data.
     *
     * @param interestRateDTO The data transfer object to be validated.
     * @return String A validation result message.
     */
    //in charge: khai
    private String isInterestRateDTOValid(InterestRateDTO interestRateDTO){
        Integer term = interestRateDTO.getTerm();
        Double rate = interestRateDTO.getInterestRate();
        Double minBalance = interestRateDTO.getMinBalance();

        // Validate term
        if(term < 1 || term > 99){
            return "Số kì phải lớn hơn 1 và bé hơn 99 tháng";
        }

        // Validate interest rate
        if(rate <= 0 || rate > 999){
            return "Lãi suất phải lớn hơn 0% và bé hơn 999%";
        }

        // Validate minimum balance
        if(minBalance <= 100000 || minBalance > 999999999){
            return "Số tiền gửi tối thiểu phải lớn hơn 100.000 và bé hơn 999.999.999";
        }

        // Check for existing interest rate with the same attributes
        if(interestRateRepository.existsByTermEqualsAndInterestRateEqualsAndMinBalanceEquals(term, rate, minBalance)){
            return "Phương thức lãi xuất này đã tồn tại";
        }

        return "OK";
    }
}
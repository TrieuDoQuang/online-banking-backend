package com.example.onlinebankingapp.services.InterestRate;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Interface defining abstract methods for Interest Rate Service.
 * This interface is responsible for handling all operations related to interest rates.
 * In charge: Khai
 */
public interface InterestRateService {
    /**
     * Inserts a new interest rate into the system.
     *
     * @param interestRateDTO Data Transfer Object containing details of the interest rate to be inserted.
     * @return InterestRateEntity The newly created interest rate entity.
     * @throws DataNotFoundException if any required data for the operation is not found.
     */
    InterestRateEntity insertInterestRate(InterestRateDTO interestRateDTO) throws DataNotFoundException;

    /**
     * Retrieves all interest rates from the system.
     *
     * @return List<InterestRateEntity> A list of all interest rate entities.
     * @throws Exception if an error occurs while retrieving the data.
     */
    List<InterestRateEntity> getAllInterestRates() throws Exception;

    /**
     * Retrieves an interest rate by its ID.
     *
     * @param id The ID of the interest rate to be retrieved.
     * @return InterestRateEntity The interest rate entity corresponding to the specified ID.
     * @throws Exception if an error occurs while retrieving the data.
     */
    InterestRateEntity getInterestRateById(Long id) throws Exception;

    /**
     * Updates an existing interest rate.
     *
     * @param id The ID of the interest rate to be updated.
     * @param interestRateDTO Data Transfer Object containing updated details of the interest rate.
     * @return InterestRateEntity The updated interest rate entity.
     * @throws Exception if an error occurs while updating the data.
     */
    InterestRateEntity updateInterestRate(Long id, InterestRateDTO interestRateDTO) throws  Exception;
}

package com.example.onlinebankingapp.services.Beneficiary;

import com.example.onlinebankingapp.dtos.BeneficiaryDTO;
import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.repositories.BeneficiaryRepository;
import com.example.onlinebankingapp.repositories.CustomerRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final CustomerRepository customerRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public BeneficiaryEntity insertBeneficiary(BeneficiaryDTO beneficiaryDTO) throws Exception {

        // Check if the beneficiary already exists
        if (beneficiaryRepository.findByCustomerIdAndAccountNumber(beneficiaryDTO.getCustomerId(), beneficiaryDTO.getAccountNumber()) != null) {
            throw new Exception("Beneficiary already exists");
        }

        // Fetch customer and payment account entities or throw an exception if not found
        CustomerEntity customer = customerRepository.findById(beneficiaryDTO.getCustomerId())
                .orElseThrow(() -> new Exception("Customer does not exist"));
        PaymentAccountEntity paymentAccount = paymentAccountRepository.getPaymentAccountByAccountNumber(beneficiaryDTO.getAccountNumber());

        if(paymentAccount == null ) {
            throw new Exception("Payment Account " + beneficiaryDTO.getAccountNumber() + " does not exist");
        }
        // Create new BeneficiaryEntity
        BeneficiaryEntity newBeneficiary = BeneficiaryEntity.builder()
                .name(beneficiaryDTO.getName())
                .customer(customer)
                .paymentAccount(paymentAccount)
                .build();

        // Save and return the new beneficiary
        return beneficiaryRepository.save(newBeneficiary);
    }

    @Override
    public BeneficiaryEntity getBeneficiaryById(long id) throws Exception {
        return beneficiaryRepository.findById(id)
                .orElseThrow(() -> new Exception("Cannot find Beneficiary with Id " + id));
    }

    @Override
    public List<BeneficiaryEntity> getBeneficiariesByCustomerId(long customerId) throws Exception {
        List<BeneficiaryEntity> beneficiaries = beneficiaryRepository.findByCustomerId(customerId);

        if (beneficiaries.isEmpty()) {
            throw new Exception("Cannot find any beneficiary for customer ID " + customerId);
        }

        return beneficiaries;
    }

    @Override
    public void deleteBeneficiary(long id) throws ExecutionException {
        beneficiaryRepository.deleteById(id);
    }
}

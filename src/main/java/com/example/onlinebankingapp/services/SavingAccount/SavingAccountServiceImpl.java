package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    @Override
    public SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) {

        return null;
    }

    @Override
    public SavingAccountEntity getSavingAccountById(Long id) throws Exception {
        return null;
    }

    @Override
    public SavingAccountEntity getAllSavingAccounts() throws Exception {
        return null;
    }

    @Override
    public SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception {
        return null;
    }

    private String isSavingAccountDTOValid(SavingAccountDTO savingAccountDTO){
//        Integer term = interestRateDTO.getTerm();
//        Double rate = interestRateDTO.getInterestRate();
//
//        if(term < 1 || term > 99){
//            return "Số kì phải lớn hơn 1 và bé hơn 99 tháng";
//        }
//
//        if(rate <= 0 || rate > 999){
//            return "Lãi suất phải lớn hơn 0% và bé hơn 999%";
//        }
//
//        if(interestRateRepository.existsByTermEqualsAndInterestRateEquals(term, rate)){
//            return "Phương thức lãi xuất này đã tồn tại";
//        }

        return "OK";
    }

}

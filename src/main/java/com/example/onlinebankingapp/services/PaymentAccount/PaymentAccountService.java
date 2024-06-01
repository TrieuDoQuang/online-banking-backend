package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.AmountOperationDTO;
import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Interface defining abstract functions for Payment Account Service.
 * In charge: dat & trieu
 */
public interface PaymentAccountService {

    /**
     * Inserts a new payment account using the provided PaymentAccountDTO.
     *
     * @param paymentAccountDTO the DTO containing payment account details
     * @return the inserted PaymentAccountEntity
     * @throws DataNotFoundException if any data required for insertion is not found
     */
    PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException;

    /**
     * Retrieves a list of all payment accounts.
     *
     * @return a list of PaymentAccountEntity objects
     * @throws DataNotFoundException if no payment accounts are found
     */
    List<PaymentAccountEntity> getAllPaymentAccounts() throws DataNotFoundException;

    /**
     * Sets the default payment account for a customer by their ID and account number.
     *
     * @param customerId the ID of the customer
     * @param accountNumber the account number to set as default
     * @throws DataNotFoundException if the customer or account is not found
     */
    void setDefaultPaymentAccount(long customerId, String accountNumber) throws DataNotFoundException;

    /**
     * Retrieves a payment account by its ID.
     *
     * @param id the ID of the payment account
     * @return the PaymentAccountEntity with the specified ID
     * @throws DataNotFoundException if the payment account is not found
     */
    PaymentAccountEntity getPaymentAccountById(long id) throws DataNotFoundException;


    /**
     * Retrieves a payment account by its account number.
     *
     * @param accountNumber the account number of the payment account
     * @return the PaymentAccountEntity with the specified account number
     * @throws DataNotFoundException if the payment account is not found
     */
    PaymentAccountEntity getPaymentAccountByAccountNumber(String accountNumber) throws DataNotFoundException;

    /**
     * Retrieves the default payment account for a customer by their ID.
     *
     * @param customerId the ID of the customer
     * @return the default PaymentAccountEntity for the customer
     * @throws DataNotFoundException if the customer or default payment account is not found
     */
    PaymentAccountEntity getDefaultPaymentAccount(long customerId) throws DataNotFoundException;

    /**
     * Retrieves a list of payment accounts for a customer by their ID.
     *
     * @param customerId the ID of the customer
     * @return a list of PaymentAccountEntity objects for the customer
     * @throws DataNotFoundException if the customer or payment accounts are not found
     */
    List<PaymentAccountEntity> getPaymentAccountsByCustomerId(long customerId) throws  DataNotFoundException;

    /**
     * Tops up a payment account with a specified amount.
     *
     * @param paymentAccountId the ID of the payment account
     * @param amountDTO the DTO containing the amount to top up
     * @throws DataNotFoundException if the payment account is not found
     */
    void topUpPaymentAccount(long paymentAccountId, AmountOperationDTO amountDTO) throws  DataNotFoundException;

    /**
     * Withdraws a specified amount from a payment account.
     *
     * @param paymentAccountId the ID of the payment account
     * @param amountDTO the DTO containing the amount to withdraw
     * @throws DataNotFoundException if the payment account is not found
     */
    void withdrawPaymentAccount (long paymentAccountId, AmountOperationDTO amountDTO) throws  DataNotFoundException;

}

package com.digitalbanking.services;

import com.digitalbanking.dtos.*;
import com.digitalbanking.exceptions.BalanceNotSufficientException;
import com.digitalbanking.exceptions.BankAccountNotFoundException;
import com.digitalbanking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    // Customer operations
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<CustomerDTO> listCustomers();
    List<CustomerDTO> searchCustomers(String keyword);

    // Account operations
    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> bankAccountList();

    // Transaction operations
    void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description)
            throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException;

    // Account history
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}

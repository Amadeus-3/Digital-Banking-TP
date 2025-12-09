package com.digitalbanking.mappers;

import com.digitalbanking.dtos.*;
import com.digitalbanking.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    // Customer mappings
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    // BankAccount mappings with polymorphism
    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
            BeanUtils.copyProperties(currentAccount, currentAccountDTO);
            currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
            currentAccountDTO.setType("CurrentAccount");
            return currentAccountDTO;
        } else if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
            BeanUtils.copyProperties(savingAccount, savingAccountDTO);
            savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
            savingAccountDTO.setType("SavingAccount");
            return savingAccountDTO;
        }
        return null;
    }

    // AccountOperation mappings
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
    }
}

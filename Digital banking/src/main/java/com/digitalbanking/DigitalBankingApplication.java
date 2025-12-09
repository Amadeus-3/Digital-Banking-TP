package com.digitalbanking;

import com.digitalbanking.dtos.BankAccountDTO;
import com.digitalbanking.dtos.CurrentAccountDTO;
import com.digitalbanking.dtos.CustomerDTO;
import com.digitalbanking.dtos.SavingAccountDTO;
import com.digitalbanking.entities.*;
import com.digitalbanking.enums.AccountStatus;
import com.digitalbanking.enums.OperationType;
import com.digitalbanking.exceptions.BalanceNotSufficientException;
import com.digitalbanking.exceptions.BankAccountNotFoundException;
import com.digitalbanking.exceptions.CustomerNotFoundException;
import com.digitalbanking.repositories.AccountOperationRepository;
import com.digitalbanking.repositories.BankAccountRepository;
import com.digitalbanking.repositories.CustomerRepository;
import com.digitalbanking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            // Create 3 Customers
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name.toLowerCase() + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });

            // Get all customers
            List<CustomerDTO> customers = bankAccountService.listCustomers();

            // Create accounts for each customer
            customers.forEach(customer -> {
                try {
                    // Create a Current Account
                    CurrentAccountDTO currentAccount = bankAccountService.saveCurrentBankAccount(
                            Math.random() * 90000,
                            9000,
                            customer.getId()
                    );

                    // Create a Saving Account
                    SavingAccountDTO savingAccount = bankAccountService.saveSavingBankAccount(
                            Math.random() * 120000,
                            5.5,
                            customer.getId()
                    );

                    // Add 5 random operations for current account
                    for (int i = 0; i < 5; i++) {
                        if (Math.random() > 0.5) {
                            bankAccountService.credit(
                                    currentAccount.getId(),
                                    1000 + Math.random() * 5000,
                                    "Credit operation " + (i + 1)
                            );
                        } else {
                            try {
                                bankAccountService.debit(
                                        currentAccount.getId(),
                                        100 + Math.random() * 2000,
                                        "Debit operation " + (i + 1)
                                );
                            } catch (BalanceNotSufficientException e) {
                                System.out.println("Balance not sufficient for debit operation");
                            }
                        }
                    }

                    // Add 5 random operations for saving account
                    for (int i = 0; i < 5; i++) {
                        if (Math.random() > 0.5) {
                            bankAccountService.credit(
                                    savingAccount.getId(),
                                    1000 + Math.random() * 5000,
                                    "Credit operation " + (i + 1)
                            );
                        } else {
                            try {
                                bankAccountService.debit(
                                        savingAccount.getId(),
                                        100 + Math.random() * 2000,
                                        "Debit operation " + (i + 1)
                                );
                            } catch (BalanceNotSufficientException e) {
                                System.out.println("Balance not sufficient for debit operation");
                            }
                        }
                    }

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("========================================");
            System.out.println("Data initialization completed!");
            System.out.println("========================================");

            // Display all bank accounts
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            bankAccounts.forEach(account -> {
                System.out.println("Account ID: " + account.getId());
                System.out.println("Type: " + account.getType());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Status: " + account.getStatus());
                System.out.println("Customer: " + account.getCustomerDTO().getName());
                System.out.println("----------------------------------------");
            });
        };
    }

    // Alternative initialization method using repositories directly (commented out)
    /*
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCurrency("USD");
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 120000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCurrency("USD");
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
    */
}

package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.AccountOperation.AccountOperationDto;
import com.example.internetbankingbackend.AccountOperation.HistoryDto;
import com.example.internetbankingbackend.CurrentAccount.CurrentAccountEntity;
import com.example.internetbankingbackend.Customer.CustomerDto;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.Exceptions.BalanceNotSufficientException;
import com.example.internetbankingbackend.Exceptions.BankAccountException;
import com.example.internetbankingbackend.Exceptions.CustomerNotFoundException;
import com.example.internetbankingbackend.SavingAccount.SavingAccountEntity;

import java.util.List;

public interface BankService {
    CustomerEntity saveCustomer(CustomerDto customer);
    CurrentAccountEntity saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountEntity saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDto> getCustomers(String keyword);
    CustomerDto getCustomer(Long id);
    List<CustomerEntity> listCustomers();
    List<CustomerDto> listCustomersDto();
    BankAccountDto getBankAccount(String accountId) throws BankAccountException;
    void debit(String accountId, double amount, String description) throws BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BalanceNotSufficientException;
    void transfer(String accountIdSource, String accountIdDestination, Double amount) throws BalanceNotSufficientException;
    List<BankAccountEntity> listBankAccount();
    CustomerDto updateCustomer(Long id, CustomerDto customerDto) throws CustomerNotFoundException;
    void deleteCustomer(Long customerId);
    List<BankAccountDto> getAllbankaccounts();
    void deleteBankAccount(String bankAccountd);
    List<AccountOperationDto> getAllAccountOperation(String bankAccountId);
    HistoryDto getAccountOperationInPage(String bankAccountId, int page, int size);
}

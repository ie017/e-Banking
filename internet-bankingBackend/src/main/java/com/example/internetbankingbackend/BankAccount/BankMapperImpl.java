package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.AccountOperation.AccountOperationDto;
import com.example.internetbankingbackend.AccountOperation.AccountOperationEntity;
import com.example.internetbankingbackend.CurrentAccount.CurrentAccountDto;
import com.example.internetbankingbackend.CurrentAccount.CurrentAccountEntity;
import com.example.internetbankingbackend.Customer.CustomerDto;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.SavingAccount.SavingAccountDto;
import com.example.internetbankingbackend.SavingAccount.SavingAccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankMapperImpl {
    public CustomerEntity fromCustomerDto(CustomerDto customerDto){
        CustomerEntity customerEntity = new CustomerEntity();/*Pour eviter d'applique les setters,
        il fait ce travail directement*/
        BeanUtils.copyProperties(customerDto, customerEntity);
        return customerEntity;
    }
    public CustomerDto fromCustomer(CustomerEntity customerEntity){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customerEntity, customerDto);
        return customerDto;
    }
    public CurrentAccountEntity fromCurrentAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
        BeanUtils.copyProperties(currentAccountDto, currentAccountEntity);
        currentAccountEntity.setCustomer(fromCustomerDto(currentAccountDto.getCustomerDto()));
        return currentAccountEntity;
    }
    public CurrentAccountDto fromCurrentAccountEntity(CurrentAccountEntity currentAccountEntity){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccountEntity, currentAccountDto);
        currentAccountDto.setCustomerDto(fromCustomer(currentAccountEntity.getCustomer()));
        return currentAccountDto;
    }
    public SavingAccountEntity fromSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        BeanUtils.copyProperties(savingAccountDto, savingAccountEntity);
        savingAccountEntity.setCustomer(fromCustomerDto(savingAccountDto.getCustomerDto()));
        return savingAccountEntity;
    }
    public SavingAccountDto fromSavingAccountEntity(SavingAccountEntity savingAccountEntity){
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(savingAccountEntity, savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomer(savingAccountEntity.getCustomer()));
        return savingAccountDto;
    }
    public AccountOperationDto fromAccountOperationEntity(AccountOperationEntity accountOperationEntity){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperationEntity, accountOperationDto);
        if (accountOperationEntity.getBankAccountEntity() instanceof SavingAccountEntity){
            accountOperationDto.setBankAccountDto(fromSavingAccountEntity((SavingAccountEntity) accountOperationEntity.getBankAccountEntity()));
        } else {
            accountOperationDto.setBankAccountDto(fromCurrentAccountEntity((CurrentAccountEntity) accountOperationEntity.getBankAccountEntity()));
        }
        return accountOperationDto;
    }
    public AccountOperationEntity fromAccountOperationDto(AccountOperationDto accountOperationDto){
        AccountOperationEntity accountOperationEntity  = new AccountOperationEntity();
        BeanUtils.copyProperties(accountOperationDto, accountOperationEntity);
        return accountOperationEntity;
    }
}

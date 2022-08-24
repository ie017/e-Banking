package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.AccountOperation.AccountOperationEntity;
import com.example.internetbankingbackend.Customer.CustomerDto;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.Enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class BankAccountDto {
    private CustomerDto customerDto;
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
}

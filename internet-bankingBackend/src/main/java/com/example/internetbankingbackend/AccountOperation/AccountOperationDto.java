package com.example.internetbankingbackend.AccountOperation;

import com.example.internetbankingbackend.BankAccount.BankAccountDto;
import com.example.internetbankingbackend.BankAccount.BankAccountEntity;
import com.example.internetbankingbackend.Enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class AccountOperationDto {
    private BankAccountDto bankAccountDto;
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
}

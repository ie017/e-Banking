package com.example.internetbankingbackend.SavingAccount;

import com.example.internetbankingbackend.BankAccount.BankAccountDto;
import lombok.Data;

@Data
public class SavingAccountDto extends BankAccountDto {
    private double interestRate;
}

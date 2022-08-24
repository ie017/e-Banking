package com.example.internetbankingbackend.CurrentAccount;

import com.example.internetbankingbackend.BankAccount.BankAccountDto;
import lombok.Data;

@Data
public class CurrentAccountDto extends BankAccountDto {
    private double overDraft;
}

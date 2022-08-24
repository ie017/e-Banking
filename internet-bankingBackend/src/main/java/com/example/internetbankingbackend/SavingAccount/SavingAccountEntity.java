package com.example.internetbankingbackend.SavingAccount;

import com.example.internetbankingbackend.BankAccount.BankAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SAVI") /* cette ligne ajouté Seulement dans la cas de SINGLE_TABLE*/
@Data
@AllArgsConstructor @NoArgsConstructor
public class SavingAccountEntity extends BankAccountEntity {
    private double interestRate;
}

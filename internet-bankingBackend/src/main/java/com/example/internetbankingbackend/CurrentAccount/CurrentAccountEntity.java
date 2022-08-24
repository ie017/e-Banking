package com.example.internetbankingbackend.CurrentAccount;

import com.example.internetbankingbackend.BankAccount.BankAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CURR") /* 4 caracteres */ /* cette ligne ajouté Seulement dans la cas de SINGLE_TABLE*/
@Data
@AllArgsConstructor @NoArgsConstructor
public class CurrentAccountEntity extends BankAccountEntity {
    private double overDraft;
}
/* JPA ne creer pas la table de currentAccountEntity parceque il herité
*BankAccountEntity et dans le dernier ona la stratigie InheritanceType.SINGLE_TABLE
* dans Inheritance */
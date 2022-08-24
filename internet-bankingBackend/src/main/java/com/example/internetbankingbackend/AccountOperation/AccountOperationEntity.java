package com.example.internetbankingbackend.AccountOperation;

import com.example.internetbankingbackend.BankAccount.BankAccountEntity;
import com.example.internetbankingbackend.Enums.OperationType;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountOperationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private BankAccountEntity bankAccountEntity;
}

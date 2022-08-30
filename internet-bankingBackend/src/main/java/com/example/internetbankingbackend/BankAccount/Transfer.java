package com.example.internetbankingbackend.BankAccount;

import lombok.Data;

@Data
public class Transfer {
    private String id;
    private double amount;
    private String description;
    private String destinationId;
}

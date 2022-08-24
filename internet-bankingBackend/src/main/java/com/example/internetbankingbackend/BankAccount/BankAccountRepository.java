package com.example.internetbankingbackend.BankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {
}

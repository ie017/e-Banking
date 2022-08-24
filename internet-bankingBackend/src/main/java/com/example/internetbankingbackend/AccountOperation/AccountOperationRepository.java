package com.example.internetbankingbackend.AccountOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperationEntity, Long> {
    List<AccountOperationEntity> findByBankAccountEntityId(String bankAccountId);
    Page<AccountOperationEntity> findByBankAccountEntityId(String bankAccountId, Pageable pageable);
}

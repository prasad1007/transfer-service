package com.example.transferservice.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.transferservice.entity.Account;

public interface AccountsRepository extends JpaRepository<Account, Long> {

  @Transactional(readOnly = true)
  Optional<Account> findByAccountNumber(final Long accountNumber);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Transactional
  @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
  Optional<Account> getAccountForUpdate(final Long accountNumber);
}

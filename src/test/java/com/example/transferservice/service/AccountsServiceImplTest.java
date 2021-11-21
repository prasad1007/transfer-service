package com.example.transferservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.transferservice.entity.Account;
import com.example.transferservice.exception.AccountNotExistException;
import com.example.transferservice.exception.NoSufficientBalanceException;
import com.example.transferservice.model.rest.TransferRequest;
import com.example.transferservice.repository.AccountsRepository;

public class AccountsServiceImplTest {

  private static final long SOURCE_ACCOUNT_NUMBER_FOR_TRANSFER_REQUEST = 34000003L;
  private static final long SOURCE_ACCOUNT_NUMBER = 34000001L;
  private static final long DESTINATION_ACCOUNT_NUMBER = 34000002L;
  private final AccountsRepository accountsRepository = mock(AccountsRepository.class);
  private final AccountsServiceImpl accountsService = new AccountsServiceImpl(accountsRepository);

  @Test
  public void testTransferBalance() throws NoSufficientBalanceException, AccountNotExistException {
    final BigDecimal amount = new BigDecimal(100);

    final TransferRequest transferRequest =
        new TransferRequest(SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, amount);

    final Account accFrom = new Account(SOURCE_ACCOUNT_NUMBER, new BigDecimal(200));
    final Account accTo = new Account(DESTINATION_ACCOUNT_NUMBER, BigDecimal.TEN);

    when(accountsRepository.getAccountForUpdate(SOURCE_ACCOUNT_NUMBER))
        .thenReturn(Optional.of(accFrom));
    when(accountsRepository.getAccountForUpdate(DESTINATION_ACCOUNT_NUMBER))
        .thenReturn(Optional.of(accTo));
    when(accountsRepository.findByAccountNumber(SOURCE_ACCOUNT_NUMBER))
        .thenReturn(Optional.of(accFrom));

    accountsService.transferBalances(transferRequest);

    assertEquals(new BigDecimal(100), accFrom.getBalance());

    verify(accountsRepository, atLeastOnce()).getAccountForUpdate(SOURCE_ACCOUNT_NUMBER);
    verify(accountsRepository, atLeastOnce()).getAccountForUpdate(DESTINATION_ACCOUNT_NUMBER);
    verify(accountsRepository, atLeastOnce()).findByAccountNumber(SOURCE_ACCOUNT_NUMBER);
  }

  @Test
  public void testForAccountNotExistException() {
    final BigDecimal amount = new BigDecimal(100);

    final TransferRequest transferRequest =
        new TransferRequest(
            SOURCE_ACCOUNT_NUMBER_FOR_TRANSFER_REQUEST, DESTINATION_ACCOUNT_NUMBER, amount);

    final Account accFrom = new Account(SOURCE_ACCOUNT_NUMBER, new BigDecimal(200));

    when(accountsRepository.getAccountForUpdate(SOURCE_ACCOUNT_NUMBER_FOR_TRANSFER_REQUEST))
        .thenReturn(Optional.of(accFrom));

    assertThrows(
        AccountNotExistException.class, () -> accountsService.transferBalances(transferRequest));

    verify(accountsRepository, atLeastOnce())
        .getAccountForUpdate(SOURCE_ACCOUNT_NUMBER_FOR_TRANSFER_REQUEST);
  }

  @Test
  public void testNoSufficientBalance() {
    final BigDecimal amount = new BigDecimal(20);

    final TransferRequest transferRequest =
        new TransferRequest(SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, amount);

    final Account accFrom = new Account(SOURCE_ACCOUNT_NUMBER, BigDecimal.TEN);
    final Account accTo = new Account(DESTINATION_ACCOUNT_NUMBER, BigDecimal.TEN);

    when(accountsRepository.getAccountForUpdate(SOURCE_ACCOUNT_NUMBER))
        .thenReturn(Optional.of(accFrom));
    when(accountsRepository.getAccountForUpdate(DESTINATION_ACCOUNT_NUMBER))
        .thenReturn(Optional.of(accTo));

    assertThrows(
        NoSufficientBalanceException.class,
        () -> accountsService.transferBalances(transferRequest));

    verify(accountsRepository, atLeastOnce()).getAccountForUpdate(SOURCE_ACCOUNT_NUMBER);
    verify(accountsRepository, atLeastOnce()).getAccountForUpdate(DESTINATION_ACCOUNT_NUMBER);
  }
}

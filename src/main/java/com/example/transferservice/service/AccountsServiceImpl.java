package com.example.transferservice.service;

import java.util.Objects;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transferservice.entity.Account;
import com.example.transferservice.exception.AccountNotExistException;
import com.example.transferservice.exception.NoSufficientBalanceException;
import com.example.transferservice.model.rest.TransferRequest;
import com.example.transferservice.model.rest.TransferResponse;
import com.example.transferservice.repository.AccountsRepository;

@Service("accountsService")
@Slf4j
public class AccountsServiceImpl implements AccountsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceImpl.class);

  @Autowired private final AccountsRepository accountsRepository;

  public AccountsServiceImpl(final AccountsRepository accountsRepository) {
    this.accountsRepository = Objects.requireNonNull(accountsRepository);
  }

  private Account retrieveBalances(final Long accountNumber) throws AccountNotExistException {
    LOGGER.info("Fetching balance for {}", accountNumber);
    return accountsRepository
        .findByAccountNumber(accountNumber)
        .orElseThrow(
            () ->
                new AccountNotExistException(
                    "Account with id:" + accountNumber + " does not exist."));
  }

  @Transactional
  public TransferResponse transferBalances(final TransferRequest transferRequest)
      throws NoSufficientBalanceException, AccountNotExistException {

    final Account sourceAccountNumber =
        accountsRepository
            .getAccountForUpdate(transferRequest.getSourceAccountNumber())
            .orElseThrow(
                () ->
                    new AccountNotExistException(
                        "Account with id:"
                            + transferRequest.getSourceAccountNumber()
                            + " does not exist."));

    final Account destinationAccountNumber =
        accountsRepository
            .getAccountForUpdate(transferRequest.getDestinationAccountNumber())
            .orElseThrow(
                () ->
                    new AccountNotExistException(
                        "Account with id:"
                            + transferRequest.getDestinationAccountNumber()
                            + " does not exist."));

    if (sourceAccountNumber.getBalance().compareTo(transferRequest.getAmount()) < 0) {
      throw new NoSufficientBalanceException(
          "Account with id:"
              + sourceAccountNumber.getAccountNumber()
              + " does not have enough balance to transferRequest.");
    }

    sourceAccountNumber.setBalance(
        sourceAccountNumber.getBalance().subtract(transferRequest.getAmount()));
    destinationAccountNumber.setBalance(
        destinationAccountNumber.getBalance().add(transferRequest.getAmount()));

    LOGGER.info(
        "Transfer completed from account {} to account {}",
        sourceAccountNumber,
        destinationAccountNumber);

    final TransferResponse transferResponse = new TransferResponse();
    transferResponse.setSourceAccountNumber(sourceAccountNumber.getAccountNumber());
    transferResponse.setBalanceAfterTransfer(
        retrieveBalances(transferRequest.getSourceAccountNumber()).getBalance());
    return transferResponse;
  }
}

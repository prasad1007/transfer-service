package com.example.transferservice.service;

import org.springframework.stereotype.Service;

import com.example.transferservice.exception.AccountNotExistException;
import com.example.transferservice.exception.NoSufficientBalanceException;
import com.example.transferservice.model.rest.TransferRequest;
import com.example.transferservice.model.rest.TransferResponse;

@Service
public interface AccountsService {

  TransferResponse transferBalances(final TransferRequest transferRequest)
      throws NoSufficientBalanceException, AccountNotExistException;
}

package com.example.transferservice.controller;

import static com.example.transferservice.constant.UriConstants.TRANSFER_SERVICE_API;
import static com.example.transferservice.constant.UriConstants.TRANSFER_URI;

import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transferservice.exception.AccountNotExistException;
import com.example.transferservice.exception.NoSufficientBalanceException;
import com.example.transferservice.model.rest.TransferRequest;
import com.example.transferservice.model.rest.TransferResponse;
import com.example.transferservice.service.AccountsService;

@RestController
@RequestMapping(TRANSFER_SERVICE_API)
public class TransferController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferController.class);

  @Autowired private final AccountsService accountService;

  private TransferController(final AccountsService accountService) {
    this.accountService = Objects.requireNonNull(accountService);
  }

  @PostMapping(TRANSFER_URI)
  public ResponseEntity<TransferResponse> transferMoney(
      @RequestBody @Valid final TransferRequest transferRequest)
      throws AccountNotExistException, NoSufficientBalanceException {
    /*TODO Prasad - We know before logging statement is logging actual account numbers. In actual implementation, this should NOT be
     *  practiced and even if we want to log them, we should encrypt them for e.g. 34*****1 */
    LOGGER.info(
        "Transferring amount from  account {} to account {}",
        transferRequest.getSourceAccountNumber(),
        transferRequest.getDestinationAccountNumber());
    return ResponseEntity.ok(accountService.transferBalances(transferRequest));
  }
}

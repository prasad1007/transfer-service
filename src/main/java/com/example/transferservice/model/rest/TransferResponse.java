package com.example.transferservice.model.rest;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferResponse {

  private Long sourceAccountNumber;

  private BigDecimal balanceAfterTransfer;
}

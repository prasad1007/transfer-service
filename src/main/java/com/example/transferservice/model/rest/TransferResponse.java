package com.example.transferservice.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransferResponse {

  private Long sourceAccountNumber;

  private BigDecimal balanceAfterTransfer;
}

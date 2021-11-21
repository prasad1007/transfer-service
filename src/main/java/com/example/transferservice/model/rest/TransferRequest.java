package com.example.transferservice.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

  @NotNull private final Long sourceAccountNumber;

  @NotNull private final Long destinationAccountNumber;

  @NotNull
  @Min(value = 0, message = "Transfer amount can not be less than zero")
  private final BigDecimal amount;

  @JsonCreator
  public TransferRequest(
      @NotNull @JsonProperty("sourceAccountNumber") final Long sourceAccountNumber,
      @NotNull @JsonProperty("destinationAccountNumber") final Long destinationAccountNumber,
      @NotNull
          @Min(value = 0, message = "Transfer amount can not be less than zero")
          @JsonProperty("amount")
          final BigDecimal amount) {
    this.sourceAccountNumber = sourceAccountNumber;
    this.destinationAccountNumber = destinationAccountNumber;
    this.amount = amount;
  }
}

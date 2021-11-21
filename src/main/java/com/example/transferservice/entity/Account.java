package com.example.transferservice.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNTS")
@Getter
@Setter
@NoArgsConstructor
public class Account {

  @Id
  @Column(name = "ACCOUNTNUMBER")
  private Long accountNumber;

  @NotNull
  @Column(name = "BALANCE")
  @Min(value = 0, message = "account balance must be positive")
  private BigDecimal balance;

  public Account(
      final Long accountNumber,
      @NotNull @Min(value = 0, message = "account balance must be positive")
          final BigDecimal balance) {
    super();
    this.accountNumber = accountNumber;
    this.balance = balance;
  }
}

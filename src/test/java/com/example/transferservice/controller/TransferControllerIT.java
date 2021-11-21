package com.example.transferservice.controller;

import static com.example.transferservice.constant.UriConstants.TRANSFER_SERVICE_API;
import static com.example.transferservice.constant.UriConstants.TRANSFER_URI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.transferservice.TransferServiceApplication;
import com.example.transferservice.model.rest.TransferRequest;
import com.example.transferservice.model.rest.TransferResponse;

@SpringBootTest(classes = TransferServiceApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class TransferControllerIT {

  private static final long SOURCE_ACCOUNT_NUMBER = 34000001L;
  private static final long DESTINATION_ACCOUNT_NUMBER = 34000002L;

  @LocalServerPort private int port;

  final TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  void testIfTransferBalanceFunctionalityIsWorkingAsExpected() {
    final TransferRequest transferRequest =
        new TransferRequest(SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, new BigDecimal(100));
    final ResponseEntity<TransferResponse> responseEntity =
        restTemplate.postForEntity(
            createURLWithPort(TRANSFER_SERVICE_API + TRANSFER_URI),
            transferRequest,
            TransferResponse.class);
    assertThat(responseEntity.getStatusCode(), is(OK));
  }

  @Test
  void testIfTransferBalanceFunctionalityIsWorkingAsExpectedWhenAccountNotExistExceptionHappens() {
    final TransferRequest transferRequest =
        new TransferRequest(34000003L, DESTINATION_ACCOUNT_NUMBER, new BigDecimal(100));
    final ResponseEntity<TransferResponse> responseEntity =
        restTemplate.postForEntity(
            createURLWithPort(TRANSFER_SERVICE_API + TRANSFER_URI),
            transferRequest,
            TransferResponse.class);
    assertThat(responseEntity.getStatusCode(), is(NOT_FOUND));
  }

  @Test
  void
      testIfTransferBalanceFunctionalityIsWorkingAsExpectedWhenNoSufficientBalanceExceptionHappens() {
    final TransferRequest transferRequest =
        new TransferRequest(SOURCE_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_NUMBER, new BigDecimal(500));
    final ResponseEntity<TransferResponse> responseEntity =
        restTemplate.postForEntity(
            createURLWithPort(TRANSFER_SERVICE_API + TRANSFER_URI),
            transferRequest,
            TransferResponse.class);
    assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
  }

  private String createURLWithPort(final String uri) {
    return "http://localhost:" + port + "/" + uri;
  }
}

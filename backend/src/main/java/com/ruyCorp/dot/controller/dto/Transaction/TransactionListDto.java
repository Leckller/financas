package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Transaction;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionListDto (

    @JsonProperty("transactions")
    List<TransactionDto> transactions,

    @JsonProperty("balance")
    Double balance

) {

  public static TransactionListDto fromEntity (Double balance, List<Transaction> transactions) {
    return new TransactionListDto(
        transactions.stream().map(TransactionDto::fromEntity).toList(),
        balance
    );
  }

}

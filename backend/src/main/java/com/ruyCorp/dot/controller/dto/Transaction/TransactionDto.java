package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Transaction;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto (

  @JsonProperty("type")
  String type,

  @JsonProperty("value")
  Double value

) {

  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(
        transaction.getType(),
        transaction.getValue()
    );
  }

}

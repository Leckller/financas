package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Transaction;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionCreationDto (

    @JsonProperty("type")
    String type,

    @JsonProperty("amount")
    Double amount

) {

  public static TransactionCreationDto fromEntity(Transaction transaction) {
    return new TransactionCreationDto(
        transaction.getType(),
        transaction.getAmount()
    );
  }

}

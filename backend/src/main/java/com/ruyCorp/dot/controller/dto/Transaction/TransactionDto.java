package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto (

  @JsonProperty("amount")
  @NotNull(message = "Não é possível criar uma transação sem um valor")
  Double amount,

  @JsonProperty("name")
  @NotBlank(message = "Não é possível criar uma transação sem um nome")
  String name

  ) {

  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(
        transaction.getAmount(),
        transaction.getName()
    );
  }

}

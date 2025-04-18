package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateTransactionDto (

  @JsonProperty("amount")
  @NotNull(message = "Não é possível criar uma transação sem um valor")
  Double amount,

  @JsonProperty("name")
  @NotBlank(message = "Não é possível criar uma transação sem um nome")
  String name

) {}

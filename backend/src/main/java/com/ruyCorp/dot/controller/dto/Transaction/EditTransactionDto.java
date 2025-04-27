package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EditTransactionDto (

  @JsonProperty("id")
  @NotNull(message = "É necessário o ID da transação para realizar a edição")
  Integer id,

  @JsonProperty("name")
  String name,

  @JsonProperty("amount")
  Double amount

) {}

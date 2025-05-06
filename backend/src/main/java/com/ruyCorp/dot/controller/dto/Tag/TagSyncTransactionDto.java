package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagSyncTransactionDto(

    @Positive(message = "O id deve ser positivo.")
    @NotNull(message = "É necessáio de um id de uma tag para realizar essa operação.")
    Integer tagId,

    @Positive(message = "O id deve ser positivo.")
    @NotNull(message = "É necessáio de um id de uma transação para realizar essa operação.")
    Integer transactionId

) {}

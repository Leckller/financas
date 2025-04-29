package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateTransactionWithTagsDto (

    @JsonProperty("amount")
    @NotNull(message = "Não é possível criar uma transação sem um valor")
    Double amount,

    @JsonProperty("name")
    @NotBlank(message = "Não é possível criar uma transação sem um nome")
    String name,

    @JsonProperty("tags")
    @NotNull(message = "Adicione pelo menos uma tag, ou passe um array vazio.")
    List<Integer> tags

) {}
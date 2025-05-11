package com.ruyCorp.dot.controller.dto.Projection;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectedTransactionDto (
    @NotBlank(message = "Não é possível criar uma transação projetada sem um nome")
    String name
) { }

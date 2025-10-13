package com.ruyCorp.dot.controller.dto.Projection;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectionDto (
    @NotBlank(message = "Não é possível criar uma projeção sem um nome.")
    String name
) {}

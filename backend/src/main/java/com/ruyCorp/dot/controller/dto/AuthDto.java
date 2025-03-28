package com.ruyCorp.dot.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthDto(
    @NotBlank(message= "O Username é obrigatório")
    @JsonProperty("username")
    String username,

    @Size(min=8, message="A senha deve ter pelo menos 8 caracteres")
    @NotBlank(message="A senha é obrigatória")
    @JsonProperty("password")
    String password
) { }

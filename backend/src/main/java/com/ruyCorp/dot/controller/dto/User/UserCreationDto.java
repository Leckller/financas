package com.ruyCorp.dot.controller.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserCreationDto(
    @NotBlank(message="O Nome é obrigatório")
    @Size(min=3, max = 20, message = "O seu nome deve ter pelo menos 3 letras e no máximo 20.")
    @JsonProperty("name")
    String name,

    @NotBlank(message="O Username é obrigatório")
    @Size(min=3, max = 20, message = "O seu nome deve ter pelo menos 3 letras e no máximo 20.")
    @JsonProperty("username")
    String username,

    @Email(message="Formato de email inválido")
    @NotBlank(message="O Email é obrigatório")
    @Size(max = 100, message = "O seu email não pode ter mais que 100 letras.")
    @JsonProperty("email")
    String email,

    @NotNull(message = "O orçamento é obrigatório")
    @Positive(message = "Seu orçamento não pode ser negativo.")
    @JsonProperty("budget")
    Double budget,

    @Size(min=8, message="A senha deve ter pelo menos 8 caracteres")
    @NotBlank(message="A senha é obrigatória")
    @JsonProperty("password")
    String password
) { }

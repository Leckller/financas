package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagCreationDto (

    @JsonProperty("name")
    @NotBlank(message = "Não é possível criar uma tag sem um nome")
    String name,

    @JsonProperty("color")
    String color
)
{ }

package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TagEditDto(
    @JsonProperty("name")
    String name,

    @JsonProperty("color")
    String color
) {}

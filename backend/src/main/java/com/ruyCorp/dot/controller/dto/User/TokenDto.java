package com.ruyCorp.dot.controller.dto.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenDto (
    @JsonProperty("token")
    String token
) {}

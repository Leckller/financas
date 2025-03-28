package com.ruyCorp.dot.service.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageDto (
    @JsonProperty("message")
    String message
) { }

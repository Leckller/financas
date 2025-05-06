package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Tag;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagDto (

  @JsonProperty("id")
  Integer id,

  @JsonProperty("name")
  String name,

  @JsonProperty("color")
  String color

) {

  public static TagDto fromEntity(Tag tag) {
    return new TagDto(
        tag.getId(),
        tag.getName(),
        tag.getColor()
    );
  }

}


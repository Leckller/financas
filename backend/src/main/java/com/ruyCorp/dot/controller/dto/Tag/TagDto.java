package com.ruyCorp.dot.controller.dto.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.repository.entity.Tag;
import jakarta.validation.constraints.NotBlank;

public record TagDto (

    @JsonProperty("name")
  @NotBlank(message = "Não é possível criar uma tag sem um nome")
  String name,

  @JsonProperty("color")
  String color

) {

  public static TagDto fromEntity(Tag tag) {
    return new TagDto(
        tag.getName(),
        tag.getColor()
    );
  }

}


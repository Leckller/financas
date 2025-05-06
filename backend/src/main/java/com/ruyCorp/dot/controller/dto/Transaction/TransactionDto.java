package com.ruyCorp.dot.controller.dto.Transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruyCorp.dot.controller.dto.Tag.TagDto;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto (

  @JsonProperty("id")
  Integer id,

  @JsonProperty("amount")
  Double amount,

  @JsonProperty("name")
  String name,

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  @JsonProperty("createdAt")
  LocalDateTime createdAt,

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  @JsonProperty("updatedAt")
  LocalDateTime updatedAt,

  @JsonProperty("tags")
  List<TagDto> tags

  ) {

  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(
        transaction.getId(),
        transaction.getAmount(),
        transaction.getName(),
        transaction.getCreatedAt(),
        transaction.getUpdatedAt(),
        transaction.getTags().stream().map(TagDto::fromEntity).toList()
    );
  }

}

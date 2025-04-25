package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Tag.TagCreationDto;
import com.ruyCorp.dot.controller.dto.Tag.TagDto;
import com.ruyCorp.dot.controller.dto.Tag.TagSyncTransactionDto;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.service.TagService;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.Tag.TagSyncTransactionNoPermissionException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

  private final TagService  tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }


  @PostMapping
  public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagCreationDto body) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Tag tag = this.tagService.createTag(body, username);

    return ResponseEntity.status(HttpStatus.CREATED).body(TagDto.fromEntity(tag));
  }

  @PostMapping("/sync")
  public ResponseEntity<MessageDto> syncTag(@Valid @RequestBody TagSyncTransactionDto dto)
      throws TagSyncTransactionNoPermissionException, NoPermissionException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.tagService.syncTagTransaction(dto, username);

    return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Tag vinculada com sucesso!"));
  }


}

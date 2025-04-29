package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Tag.*;
import com.ruyCorp.dot.controller.dto.Transaction.CreateTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.service.TagService;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.Tag.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

  private final TagService  tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping("/transaction")
  public ResponseEntity<TransactionDto> newTransactionWithTags(
      @Valid @RequestBody CreateTransactionWithTagsDto dto) throws MaxTagsTransactionsExceptions, TagRepeatedException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Transaction transaction = this.tagService.createTransactionWithTags(username, dto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(TransactionDto.fromEntity(transaction));

  }

  @PostMapping
  public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagCreationDto body) throws TagAlreadyExistsException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Tag tag = this.tagService.createTag(body, username);

    return ResponseEntity.status(HttpStatus.CREATED).body(TagDto.fromEntity(tag));
  }

  @PostMapping("/sync")
  public ResponseEntity<MessageDto> syncTag(@Valid @RequestBody TagSyncTransactionDto dto)
      throws TagSyncTransactionNoPermissionException, NoPermissionException, MaxTagsTransactionsExceptions {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    MessageDto syncTagTransaction = this.tagService.syncTagTransaction(dto, username);

    return ResponseEntity.status(HttpStatus.OK).body(syncTagTransaction);
  }

  @GetMapping
  public ResponseEntity<List<TagDto>> listTags(Pageable pageable) {

    List<Tag> tags = this.tagService.listTags(pageable);

    return ResponseEntity.status(HttpStatus.OK).body(tags.stream().map(TagDto::fromEntity).toList());

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDto> deleteTag(@PathVariable Integer id) throws TagNotBelongsUserException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.tagService.deleteTag(id, username);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Tag deletada com sucesso."));

  }

  @PatchMapping("{id}")
  public ResponseEntity<TagDto> editTag(@PathVariable Integer id, @Valid @RequestBody TagEditDto body)
      throws TagNotBelongsUserException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Tag tag = this.tagService.editTag(id, body, username);

    return ResponseEntity.status(HttpStatus.OK).body(TagDto.fromEntity(tag));

  }

}

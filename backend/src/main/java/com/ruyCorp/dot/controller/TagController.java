package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Tag.TagCreationDto;
import com.ruyCorp.dot.controller.dto.Tag.TagDto;
import com.ruyCorp.dot.controller.dto.Tag.TagEditDto;
import com.ruyCorp.dot.controller.dto.Tag.TagSyncTransactionDto;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.service.TagService;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.Tag.MaxTagsTransactionsExceptions;
import com.ruyCorp.dot.service.exception.Tag.TagNotBelongsUserException;
import com.ruyCorp.dot.service.exception.Tag.TagSyncTransactionNoPermissionException;
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


  @PostMapping
  public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagCreationDto body) {

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

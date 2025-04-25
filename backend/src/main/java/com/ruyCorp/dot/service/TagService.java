package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Tag.TagCreationDto;
import com.ruyCorp.dot.controller.dto.Tag.TagSyncTransactionDto;
import com.ruyCorp.dot.repository.TagRepository;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.NotFound.TransactionNotFoundException;
import com.ruyCorp.dot.service.exception.NotFound.UserNotFoundException;
import com.ruyCorp.dot.service.exception.Tag.TagAlreadyExistsException;
import com.ruyCorp.dot.service.exception.Tag.TagNotFoundException;
import com.ruyCorp.dot.service.exception.Tag.TagSyncTransactionNoPermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final UserService userService;
  private final TransactionService transactionService;

  @Autowired
  public TagService(TagRepository tagRepository, UserService userService, TransactionService transactionService) {
    this.tagRepository = tagRepository;
    this.userService = userService;
    this.transactionService = transactionService;
  }

  public Tag createTag(TagCreationDto dto, String username) throws TagAlreadyExistsException {

    tagExists(dto.name());

    User user = this.userService.findByUsername(username);

    Tag tag = new Tag();

    tag.setName(dto.name());

    if(dto.color() == null) {
      tag.setColor("#0bb9ff");
    } else {
      tag.setColor(dto.color());
    }

    tag.setUser(user);

    return this.tagRepository.save(tag);

  }

  public void syncTagTransaction(TagSyncTransactionDto dto, String username)
      throws NoPermissionException, TagNotFoundException, UserNotFoundException, TransactionNotFoundException, TagSyncTransactionNoPermissionException {

    User user = this.userService.findByUsername(username);
    Tag tag = this.getTagById(dto.tagId());
    Transaction transaction = this.transactionService.getTransactionById(dto.tagId());

    transactionService.userHavePermission(user, transaction);
    userHavePermission(user.getId(), transaction);

    tag.getTransactions().add(transaction);

    this.tagRepository.save(tag);

  }

  public List<Tag> listTags (Pageable pageable) {
    return this.tagRepository.listWithPages(pageable).getContent();
  }

  public void deleteTag(Integer id, String username) {

    Tag tag = this.getTagById(id);
    User user = this.userService.findByUsername(username);


  }

  public void userHavePermission(Integer userId, Transaction transaction) throws TagSyncTransactionNoPermissionException {
    if(!Objects.equals(transaction.getUser().getId(), userId)) {
      throw new TagSyncTransactionNoPermissionException();
    }
  }

  public void tagExists(String name) {
    Optional<Tag> tag = this.tagRepository.findByName(name);
    if(tag.isPresent()) {
      throw new TagAlreadyExistsException();
    }
  }

  public Tag getTagById(Integer tagId) throws TagNotFoundException {
    return this.tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
  }

  public Tag tagIsCreatedByUser(Integer tagId, String username) throws NoPermissionException, TagNotFoundException {
    Tag tag = this.getTagById(tagId);
    User user = this.userService.findByUsername(username);
    if (!Objects.equals(tag.getUser().getId(), user.getId())) {
      throw new NoPermissionException();
    }
    return tag;
  }

}

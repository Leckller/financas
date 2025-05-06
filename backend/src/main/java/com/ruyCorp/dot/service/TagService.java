package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Tag.CreateTransactionWithTagsDto;
import com.ruyCorp.dot.controller.dto.Tag.TagCreationDto;
import com.ruyCorp.dot.controller.dto.Tag.TagEditDto;
import com.ruyCorp.dot.controller.dto.Tag.TagSyncTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.CreateTransactionDto;
import com.ruyCorp.dot.repository.TagRepository;
import com.ruyCorp.dot.repository.TransactionRepository;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.NotFound.TransactionNotFoundException;
import com.ruyCorp.dot.service.exception.NotFound.UserNotFoundException;
import com.ruyCorp.dot.service.exception.Tag.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TransactionRepository transactionRepository;
  private final UserService userService;
  private final TransactionService transactionService;

  @Autowired
  public TagService(TagRepository tagRepository, TransactionRepository transactionRepository, UserService userService, TransactionService transactionService) {
    this.tagRepository = tagRepository;
    this.transactionRepository = transactionRepository;
    this.userService = userService;
    this.transactionService = transactionService;
  }

  public Transaction createTransactionWithTags(String username, CreateTransactionWithTagsDto dto) throws MaxTagsTransactionsExceptions, TagRepeatedException {
    Transaction transaction = new Transaction();
    User user = this.userService.findByUsername(username);

    transaction.setName(dto.name());
    transaction.setAmount(dto.amount());
    transaction.setUser(user);

    if(dto.tags() != null) {

      if (dto.tags().size() > 5) {
        throw new MaxTagsTransactionsExceptions();
      }

      HashSet<Integer> uniqueTags = new HashSet<>(dto.tags());
      if (uniqueTags.size() != dto.tags().size()) {
        throw new TagRepeatedException();
      }

      dto.tags().forEach(tagId -> {
        Tag tag = this.getTagById(tagId);
        transaction.getTags().add(tag);
      });

    }

    return this.transactionRepository.save(transaction);
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

  @Transactional
  public MessageDto syncTagTransaction(TagSyncTransactionDto dto, String username)
      throws NoPermissionException, TagNotFoundException, UserNotFoundException, TransactionNotFoundException, TagSyncTransactionNoPermissionException, MaxTagsTransactionsExceptions {

    Tag tag = this.getTagById(dto.tagId());

    Transaction transaction = this.transactionService.getTransactionById(dto.transactionId());

    if (transaction.getTags().size() > 5) {
      throw new MaxTagsTransactionsExceptions();
    }

    if (transaction.getTags().contains(tag)) {
      transaction.getTags().remove(tag);
      transactionRepository.save(transaction);
      return new MessageDto("Transação desvinculada da tag com sucesso.");
    }

    User user = this.userService.findByUsername(username);

    transactionService.userHavePermission(user, transaction);
    userHavePermission(user.getId(), transaction);

    transaction.getTags().add(tag);
    transactionRepository.save(transaction);

    return new MessageDto("Transação vinculada à tag com sucesso.");
  }

  public Tag editTag(Integer tagId, TagEditDto dto, String username) throws TagNotBelongsUserException {

    Tag tag = tagBelongsUser(tagId, username);

    if(dto.name() != null) {
      tag.setName(dto.name());
    }
    if(dto.color() != null) {
      tag.setColor(dto.color());
    }

    return this.tagRepository.save(tag);

  }

  public List<Tag> listTags (Pageable pageable) {
    return this.tagRepository.findAll(pageable).getContent();
  }

  public void deleteTag(Integer id, String username) throws TagNotBelongsUserException {
    Tag tag = this.tagBelongsUser(id, username);
    this.tagRepository.delete(tag);
  }

  public Tag tagBelongsUser(Integer tagId, String username) throws TagNotBelongsUserException {
    Tag tag = this.getTagById(tagId);
    User user = this.userService.findByUsername(username);
    if (!Objects.equals(tag.getUser().getId(), user.getId())) {
      throw new TagNotBelongsUserException();
    }
    return tag;
  }

  public void userHavePermission(Integer userId, Transaction transaction) throws TagSyncTransactionNoPermissionException {
    if(!Objects.equals(transaction.getUser().getId(), userId)) {
      throw new TagSyncTransactionNoPermissionException();
    }
  }

  public void tagExists(String name) throws TagAlreadyExistsException {
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

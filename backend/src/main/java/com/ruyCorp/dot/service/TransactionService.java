package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Transaction.CreateTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.EditTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionListDto;
import com.ruyCorp.dot.repository.TransactionRepository;
import com.ruyCorp.dot.repository.entity.Tag;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.NotFound.NotFoundException;
import com.ruyCorp.dot.service.exception.NotFound.TransactionNotFoundException;
import com.ruyCorp.dot.service.exception.Tag.MaxTagsTransactionsExceptions;
import com.ruyCorp.dot.service.exception.Tag.TagRepeatedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final UserService userService;

  public TransactionService(TransactionRepository transactionRepository, UserService userService) {
    this.transactionRepository = transactionRepository;
    this.userService = userService;
  }

  public Double countTransactionValues(List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getAmount)
        .reduce(0d, Double::sum);
  }

  public TransactionListDto listTransactions(String username, Pageable pageable, LocalDateTime dataInit, LocalDateTime dataFim) {

    User user = userService.findByUsername(username);

    Page<Transaction> transactions = this.transactionRepository.findByUserAndOptionalData(user, dataInit, dataFim, pageable);

    return new TransactionListDto(transactions.getContent().stream().map(TransactionDto::fromEntity).toList(), user.getMoney());

  }

  public Transaction newTransaction(String username, CreateTransactionDto dto)
      throws MaxTagsTransactionsExceptions, InvalidFieldsException, TagRepeatedException {

    Transaction transaction = new Transaction();
    User user = this.userService.findByUsername(username);

    transaction.setName(dto.name());
    transaction.setAmount(dto.amount());
    transaction.setUser(user);

    user.setMoney(user.getMoney() + transaction.getAmount());

    return this.transactionRepository.save(transaction);
  }

  public Transaction getTransactionByIdAndUsername(Integer id, String username) throws TransactionNotFoundException, NoPermissionException {
    Transaction transaction = this.getTransactionById(id);

    if (!Objects.equals(transaction.getUser().getUsername(), username)) {
      throw new NoPermissionException();
    }

    return transaction;

  }

  public Transaction getTransactionById(Integer id) throws TransactionNotFoundException {
    return this.transactionRepository.findById(id)
        .orElseThrow(TransactionNotFoundException::new);
  }

  public Transaction editTransaction(
      EditTransactionDto dto, String username) throws NoPermissionException {

    Transaction transaction = getTransactionById(dto.id());
    User user = this.userService.findByUsername(username);

    this.userHavePermission(user, transaction);

    if(dto.amount() != null) {
      user.setMoney(user.getMoney() + (transaction.getAmount() * -1));
      transaction.setAmount(dto.amount());
      user.setMoney(user.getMoney() + dto.amount());
    }
    if(dto.name() != null) {
      transaction.setName(dto.name());
    }

    return this.transactionRepository.save(transaction);

  }

  public void userHavePermission(User user, Transaction transaction) throws NoPermissionException {
    if(!Objects.equals(transaction.getUser().getId(), user.getId())) {
      throw new NoPermissionException();
    }
  }

  public void deleteTransaction(Integer id, String username) throws NoPermissionException, NotFoundException {

    User user = this.userService.findByUsername(username);

    Transaction transaction = this.getTransactionById(id);

    this.userHavePermission(user, transaction);

    user.setMoney(user.getMoney() + (transaction.getAmount() * -1));

    this.transactionRepository.delete(transaction);

  }

}

package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.repository.TransactionRepository;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.NotFound.TransactionNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

  public List<Transaction> listTransactions(String username, Pageable pageable, LocalDateTime dataInit, LocalDateTime dataFim) {

    User user = userService.findByUsername(username);

    Page<Transaction> transactions = this.transactionRepository.findByUserAndOptionalData(user, dataInit, dataFim, pageable);

    return transactions.getContent();

  }

  public Transaction newTransaction(String username, TransactionDto dto)
      throws InvalidFieldsException {

    Transaction transaction = new Transaction();
    User user = this.userService.findByUsername(username);

    transaction.setName(dto.name());
    transaction.setAmount(dto.amount());
    transaction.setUser(user);

    return this.transactionRepository.save(transaction);

  }

  public Transaction getTransactionById(Integer id) throws TransactionNotFoundException {
    return this.transactionRepository.findById(id)
        .orElseThrow(TransactionNotFoundException::new);
  }

  public Transaction editTransaction(
      TransactionDto dto, Integer id, String username) throws NoPermissionException {

    Transaction transaction = getTransactionById(id);
    User user = this.userService.findByUsername(username);

    this.userHavePermission(user, transaction);

    if(dto.amount() != null) {
      transaction.setAmount(dto.amount());
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

  public void deleteTransaction(Integer id, String username) throws NoPermissionException {

    User user = this.userService.findByUsername(username);

    Transaction transaction = this.getTransactionById(id);

    this.userHavePermission(user, transaction);

    this.transactionRepository.delete(transaction);

  }

}

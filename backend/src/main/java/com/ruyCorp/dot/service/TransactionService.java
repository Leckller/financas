package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.repository.TransactionRepository;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

  public List<Transaction> getTransactions(String username, Integer page) {

    Pageable pageable = PageRequest.of(page, 10);

    User user = userService.findByUsername(username);

    return this.transactionRepository.findAllByUser(user, pageable).getContent();

  }

  public Transaction newTransaction(String username, TransactionDto dto)
      throws InvalidFieldsException {

    if (!Objects.equals(dto.type(), "receita") && !Objects.equals(dto.type(), "despesa")) {
      throw new InvalidFieldsException("A transação só pode ser do tipo despesa ou receita.");
    }

    Transaction transaction = new Transaction(dto);

    User user = this.userService.findByUsername(username);

    if(dto.type().equals("receita")) {
      user.incrementBalance(dto.amount());
    } else {
      user.decrementBalance(dto.amount());
    }

    transaction.setUser(user);

    return this.transactionRepository.save(transaction);

  }

}

package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  // Tem q criar uma lógica para verificar pelo tempo
  @GetMapping
  public ResponseEntity<List<TransactionDto>> listTransactions () {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // !!!!!! provisório.
    List<Transaction> transactions = this.transactionService.getTransactions(username, 0);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(transactions.stream().map(TransactionDto::fromEntity).toList());
  }

}

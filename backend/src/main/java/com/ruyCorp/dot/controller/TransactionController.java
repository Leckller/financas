package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionListDto;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.TransactionService;
import com.ruyCorp.dot.service.UserService;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;
  private final UserService userService;

  @Autowired
  public TransactionController(TransactionService transactionService, UserService userService) {
    this.transactionService = transactionService;
    this.userService = userService;
  }

  // Tem q criar uma lógica para verificar pelo tempo
  @GetMapping
  public ResponseEntity<TransactionListDto> listTransactions () {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // !!!!!! provisório.
    List<Transaction> transactions = this.transactionService.listTransactions(username, 0);
    User user = this.userService.findByUsername(username);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(TransactionListDto.fromEntity(user.getBalance(), transactions));
  }

  @PostMapping
  public ResponseEntity<TransactionDto> newTransaction(
      @Valid @RequestBody TransactionDto dto) throws InvalidFieldsException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Transaction transaction = this.transactionService.newTransaction(username, dto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(TransactionDto.fromEntity(transaction));

  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteTransaction(@PathVariable Integer id)
      throws NoPermissionException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    this.transactionService.deleteTransaction(id, username);

    return ResponseEntity.status(HttpStatus.OK).build();

  }

}

package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionListDto;
import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.service.TransactionService;
import com.ruyCorp.dot.service.exception.InvalidFieldsException;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("{id}")
  public ResponseEntity<TransactionDto> getTransactionById(@PathVariable int id) throws NoPermissionException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    Transaction transaction = this.transactionService.getTransactionByIdAndUsername(id, username);

    return ResponseEntity.status(HttpStatus.OK).body(TransactionDto.fromEntity(transaction));

  }

  @GetMapping
  public ResponseEntity<TransactionListDto> listTransactions (
      @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInit,
      @RequestParam(value = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
      Pageable pageable
  ) throws UsernameNotFoundException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    List<Transaction> transactions = this.transactionService.listTransactions(username, pageable, dataInit, dataFim);

    Double balance = this.transactionService.countTransactionValues(transactions);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(TransactionListDto.fromEntity(balance, transactions));
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

  @PatchMapping("{id}")
  public ResponseEntity<TransactionDto> editTransaction(
      @Valid @RequestBody TransactionDto dto, @PathVariable Integer id) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();


    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteTransaction(@PathVariable Integer id)
      throws NoPermissionException {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    this.transactionService.deleteTransaction(id, username);

    return ResponseEntity.status(HttpStatus.OK).build();

  }

}

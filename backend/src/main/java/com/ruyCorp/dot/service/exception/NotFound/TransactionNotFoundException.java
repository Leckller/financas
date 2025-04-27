package com.ruyCorp.dot.service.exception.NotFound;

public class TransactionNotFoundException extends NotFoundException {
  public TransactionNotFoundException() {
    super("Transação não encontrada.");
  }
}

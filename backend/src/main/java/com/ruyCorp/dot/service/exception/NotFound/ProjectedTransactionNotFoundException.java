package com.ruyCorp.dot.service.exception.NotFound;

public class ProjectedTransactionNotFoundException extends NotFoundException {
  public ProjectedTransactionNotFoundException() {
    super("Transação projetada não encontrada.");
  }
}

package com.ruyCorp.dot.service.exception.NotFound;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException () {
    super("Usuário não encontrado!");
  }
}

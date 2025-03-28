package com.ruyCorp.dot.service.exception.AlreadyExists;

public class UserAlreadyExistsException extends AlreadyExistsException {
  public UserAlreadyExistsException() {
    super("Este usuário já existe!");
  }
}

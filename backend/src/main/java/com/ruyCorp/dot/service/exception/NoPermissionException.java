package com.ruyCorp.dot.service.exception;

import org.apache.coyote.BadRequestException;

public class NoPermissionException extends BadRequestException {
  public NoPermissionException() {
    super("Você não tem permissão para fazer isso.");
  }
}

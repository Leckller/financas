package com.ruyCorp.dot.service.exception.AlreadyExists;

import org.apache.coyote.BadRequestException;

public class AlreadyExistsException extends BadRequestException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}

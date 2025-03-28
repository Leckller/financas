package com.ruyCorp.dot.service.exception;

import org.apache.coyote.BadRequestException;

public class InvalidFieldsException extends BadRequestException {
  public InvalidFieldsException(String message) {
    super(message);
  }
}

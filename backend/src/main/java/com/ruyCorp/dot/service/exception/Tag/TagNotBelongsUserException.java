package com.ruyCorp.dot.service.exception.Tag;

import org.apache.coyote.BadRequestException;

public class TagNotBelongsUserException extends BadRequestException {
  public TagNotBelongsUserException() {
    super("Essa tag não pertence a você.");
  }
}

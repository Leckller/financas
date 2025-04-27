package com.ruyCorp.dot.service.exception.Tag;

import com.ruyCorp.dot.service.exception.AlreadyExists.AlreadyExistsException;

public class TagAlreadyExistsException extends AlreadyExistsException {
  public TagAlreadyExistsException() {
    super("Essa tag já existe");
  }
}

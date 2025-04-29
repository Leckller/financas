package com.ruyCorp.dot.service.exception.Tag;

import org.apache.coyote.BadRequestException;

public class TagRepeatedException extends BadRequestException {

  public TagRepeatedException() {
    super("Não é possível adicionar a mesma tag mais de uma vez.");
  }

}

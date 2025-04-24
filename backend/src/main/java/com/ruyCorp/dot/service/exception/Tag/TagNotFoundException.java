package com.ruyCorp.dot.service.exception.Tag;

import com.ruyCorp.dot.service.exception.NotFound.NotFoundException;

public class TagNotFoundException extends NotFoundException {
  public TagNotFoundException() {
    super("Tag não encontrada");
  }
}

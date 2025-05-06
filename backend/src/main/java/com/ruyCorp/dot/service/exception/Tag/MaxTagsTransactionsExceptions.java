package com.ruyCorp.dot.service.exception.Tag;

import org.apache.coyote.BadRequestException;

public class MaxTagsTransactionsExceptions extends BadRequestException {
  public MaxTagsTransactionsExceptions() {
    super("Uma transação só pode ter no máximo 5 tags!");
  }
}

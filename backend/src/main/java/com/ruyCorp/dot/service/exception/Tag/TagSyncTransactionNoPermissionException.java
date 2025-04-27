package com.ruyCorp.dot.service.exception.Tag;

import com.ruyCorp.dot.service.exception.NoPermissionException;
import org.apache.coyote.BadRequestException;

public class TagSyncTransactionNoPermissionException extends BadRequestException {
  public TagSyncTransactionNoPermissionException() {
    super("Você não tem permissão para vincular tags a esta transação.");
  }
}

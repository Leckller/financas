package com.ruyCorp.dot.service.exception;

import com.ruyCorp.dot.service.exception.AlreadyExists.AlreadyExistsException;
import com.ruyCorp.dot.service.exception.NotFound.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerManager {

  @ExceptionHandler()
  public ResponseEntity<MessageDto> handleNotFound(NotFoundException exception) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new MessageDto(exception.getMessage()));
  }

  @ExceptionHandler()
  public ResponseEntity<MessageDto> handleInvalidFields(BadRequestException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new MessageDto(exception.getMessage()));
  }

  @ExceptionHandler()
  public ResponseEntity<MessageDto> handleAlreadyExists(AlreadyExistsException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new MessageDto(exception.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationError(ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidError(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler()
  public ResponseEntity<String> handlerDefault(Exception exception) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Erro interno não tratado - " + exception.getMessage());
  }

}

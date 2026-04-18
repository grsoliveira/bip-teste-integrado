package com.example.backend;

import jakarta.ejb.EJBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EJBException.class)
  public ResponseEntity<String> handleEJBException(EJBException ex) {
    return ResponseEntity.status(404).body(ex.getMessage());
  }
}
package com.verdiance.wisiee.Exception.Stock;

public class OutOfStockException extends RuntimeException {
  public OutOfStockException(String message) {
    super(message);
  }
}


package com.innowise.aws.sqs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SqsProcessingFailedException extends RuntimeException {
  private HttpStatus httpStatus;

  public SqsProcessingFailedException(String message) {
    super(message);
  }

  public SqsProcessingFailedException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}

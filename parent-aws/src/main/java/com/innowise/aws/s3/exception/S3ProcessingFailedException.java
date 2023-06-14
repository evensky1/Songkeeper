package com.innowise.aws.s3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class S3ProcessingFailedException extends Exception {
  private final String reasonMessage;
  private HttpStatus httpStatus;
}

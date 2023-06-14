package com.innowise.aws.s3.model;

import java.util.Objects;

public record S3ObjectPath(
    String fileName,
    String destinationName) {

  public String getKey() {

    return !Objects.isNull(destinationName())
        ? String.format("%s/%s", destinationName(), fileName())
        : fileName();
  }

  @Override
  public String toString() {
    return destinationName + "/" + fileName;
  }
}

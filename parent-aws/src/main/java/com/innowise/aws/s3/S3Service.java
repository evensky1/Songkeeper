package com.innowise.aws.s3;

import com.innowise.aws.s3.model.S3ObjectPath;
import com.innowise.aws.s3.exception.S3ProcessingFailedException;
import java.io.InputStream;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface S3Service {

  PutObjectResponse putObject(S3ObjectPath s3ObjectPath, InputStream object)
      throws S3ProcessingFailedException;

  byte[] getObjectAsByteArray(String key) throws S3ProcessingFailedException;
  InputStream getObjectAsInputStream(String key) throws S3ProcessingFailedException;

}

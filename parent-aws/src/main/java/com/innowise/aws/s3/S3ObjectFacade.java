package com.innowise.aws.s3;

import com.innowise.aws.s3.model.S3ObjectPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Lazy
@Component
@Slf4j
class S3ObjectFacade {

  public PutObjectRequest getPutObjectRequest(S3ObjectPath s3PutObject) {
    var key = s3PutObject.getKey();

    log.debug("Key for put {}", key);

    return PutObjectRequest.builder()
        .bucket("songkeeper-bucket")
        .key(key)
        .build();
  }

  public GetObjectRequest getGetObjectRequest(String key) {

    log.debug("Key for get {}", key);

    return GetObjectRequest.builder()
        .bucket("songkeeper-bucket")
        .key(key)
        .build();
  }
}

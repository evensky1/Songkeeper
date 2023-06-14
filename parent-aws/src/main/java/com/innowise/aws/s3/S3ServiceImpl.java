package com.innowise.aws.s3;

import com.innowise.aws.s3.model.S3ObjectPath;
import com.innowise.aws.s3.exception.S3ProcessingFailedException;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Response;

@Service
@ConditionalOnBean(S3Configuration.class)
@RequiredArgsConstructor
@Slf4j
class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final S3ObjectFacade s3ObjectFacade;

    @Override
    public PutObjectResponse putObject(S3ObjectPath s3ObjectPath, InputStream object)
        throws S3ProcessingFailedException {

        log.info("putObject {}", s3ObjectPath);

        var putObjectRequest = s3ObjectFacade.getPutObjectRequest(s3ObjectPath);

        try {
            var putObjectResponse = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(object, object.available()));

            return validateS3Response(putObjectResponse);
        } catch (AwsServiceException | SdkClientException | IOException e) {
            log.error("Could not put object to s3", e);
            throw new S3ProcessingFailedException("Uploading operation to bucket failed");
        }
    }

    @Override
    public byte[] getObjectAsByteArray(String key) throws S3ProcessingFailedException {

        var getObjectRequest = s3ObjectFacade.getGetObjectRequest(key);

        try {
            log.info("getObjectAsByteArray {}", getObjectRequest.key());
            var getObjectResponse =
                s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());

            validateS3Response(getObjectResponse.response());

            return getObjectResponse.asByteArray();
        } catch (AwsServiceException | SdkClientException | UnsupportedOperationException e) {
            log.error("Could not get objectAsByteArray from s3", e);
            throw new S3ProcessingFailedException("Select operation from bucket failed");
        }
    }

    @Override
    public InputStream getObjectAsInputStream(String key) throws S3ProcessingFailedException {

        var getObjectRequest = s3ObjectFacade.getGetObjectRequest(key);

        try {
            log.info("getObjectAsInputStream {}", getObjectRequest.key());
            var getObjectResponse =
                s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());

            validateS3Response(getObjectResponse.response());

            return getObjectResponse.asInputStream();
        } catch (AwsServiceException | SdkClientException | UnsupportedOperationException e) {
            log.error("Could not get getObjectAsInputStream from s3", e);
            throw new S3ProcessingFailedException("Select operation from bucket failed");
        }
    }

    private <T extends S3Response> T validateS3Response(T s3Response)
        throws S3ProcessingFailedException {
        var sdkHttpResponse = s3Response.sdkHttpResponse();

        if (!sdkHttpResponse.isSuccessful()) {
            throw new S3ProcessingFailedException(
                sdkHttpResponse.statusText()
                    .orElse("Sdk http client is failed to obtain request"),
                HttpStatus.valueOf(sdkHttpResponse.statusCode()));
        }

        return s3Response;
    }
}

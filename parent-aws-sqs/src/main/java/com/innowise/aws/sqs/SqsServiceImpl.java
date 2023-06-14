package com.innowise.aws.sqs;

import com.innowise.aws.sqs.exception.SqsProcessingFailedException;
import com.innowise.aws.sqs.model.SqsMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsResponse;

@Service
@ConditionalOnBean(SqsConfiguration.class)
@RequiredArgsConstructor
@Slf4j
class SqsServiceImpl implements SqsService {

    private final SqsClient sqsClient;
    private final SqsObjectFacade sqsObjectFacade;

    @Override
    public String createQueue(String queueName) {
        var createQueueRequest = sqsObjectFacade.getCreateQueueRequest(queueName);

        try {
            log.info("creating queue {}", queueName);

            var createQueueResponse = sqsClient.createQueue(createQueueRequest);

            return validateS3Response(createQueueResponse).queueUrl();
        } catch (AwsServiceException | SdkClientException e) {
            log.error("Could not create queue sqs", e);
            throw new SqsProcessingFailedException("Creating queue failed");
        }
    }

    @Override
    public SendMessageResponse sendMessage(SqsMessage sqsMessage)
        throws SqsProcessingFailedException {
        var sendMessageRequest = sqsObjectFacade.getSendMessageRequest(sqsMessage);

        try {
            log.info("send message {}", sqsMessage);

            var sendMessageResponse = sqsClient.sendMessage(sendMessageRequest);

            return validateS3Response(sendMessageResponse);
        } catch (AwsServiceException | SdkClientException e) {
            log.error("Could not send message to sqs", e);
            throw new SqsProcessingFailedException("Sending message to queue failed");
        }
    }

    @Override
    public List<String> receiveMessages(String url) throws SqsProcessingFailedException {
        var receiveMessageRequest = sqsObjectFacade.getReceiveMessageRequest(url);

        try {
            log.info("receiveMessage {}", receiveMessageRequest.receiveRequestAttemptId());

            var receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);

            return validateS3Response(receiveMessageResponse)
                .messages()
                .stream()
                .map(Message::body)
                .toList();

        } catch (AwsServiceException | SdkClientException | UnsupportedOperationException e) {
            log.error("Could not receive messages from sqs", e);
            throw new SqsProcessingFailedException("Receive operation from queue failed");
        }
    }

    private <T extends SqsResponse> T validateS3Response(T s3Response)
        throws SqsProcessingFailedException {
        var sdkHttpResponse = s3Response.sdkHttpResponse();

        if (!sdkHttpResponse.isSuccessful()) {

            throw new SqsProcessingFailedException(
                sdkHttpResponse.statusText()
                    .orElse("Sdk http client is failed to obtain request"),
                HttpStatus.valueOf(sdkHttpResponse.statusCode()));
        }

        return s3Response;
    }
}

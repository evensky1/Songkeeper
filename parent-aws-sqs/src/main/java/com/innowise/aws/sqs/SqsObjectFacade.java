package com.innowise.aws.sqs;

import com.innowise.aws.sqs.model.SqsMessage;
import java.util.EnumMap;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@Slf4j
class SqsObjectFacade {

    public DeleteMessageRequest getDeleteMessageRequest(String queueUrl, String receiptHandle) {

        return DeleteMessageRequest.builder()
            .queueUrl(queueUrl)
            .receiptHandle(receiptHandle)
            .build();
    }

    public CreateQueueRequest getCreateQueueRequest(String queueName) {

        log.debug("Creating queue {}", queueName);

        var attributes = new EnumMap<QueueAttributeName, String>(QueueAttributeName.class);

        attributes.put(QueueAttributeName.CONTENT_BASED_DEDUPLICATION, "true");
        attributes.put(QueueAttributeName.FIFO_QUEUE, "true");

        return CreateQueueRequest.builder()
            .queueName(queueName)
            .attributes(attributes)
            .build();
    }

    public SendMessageRequest getSendMessageRequest(SqsMessage sqsMessage) {

        log.debug("Sending {} to {}", sqsMessage.message(), sqsMessage.queueUrl());

        return SendMessageRequest.builder()
            .queueUrl(sqsMessage.queueUrl())
            .messageGroupId(sqsMessage.groupId())
            .messageBody(sqsMessage.message())
            .build();
    }

    public ReceiveMessageRequest getReceiveMessageRequest(String queueUrl) {

        log.debug("Getting from {}", queueUrl);

        return ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .build();
    }

    public ReceiveMessageRequest getReceiveMessageRequest(
        String queueUrl,
        Integer maxNumberOfMessages,
        Integer waitTimeSeconds,
        Integer visibilityTimeout) {

        return ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(maxNumberOfMessages)
            .waitTimeSeconds(waitTimeSeconds)
            .visibilityTimeout(visibilityTimeout)
            .build();
    }
}

package com.innowise.aws.sqs.model;

public record SqsMessage(
    String queueUrl,
    String groupId,
    String message) {

}

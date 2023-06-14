package com.innowise.aws.sqs.model;

@FunctionalInterface
public interface SqsMessageBodyHandler {

    void handle(String message);
}

package com.innowise.aws.sqs.exception;

public class ListenerInitializationException extends RuntimeException {

    public ListenerInitializationException(String message) {
        super(message);
    }
}

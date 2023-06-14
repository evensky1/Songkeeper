package com.innowise.aws.sqs;

import com.innowise.aws.sqs.exception.ListenerInitializationException;
import com.innowise.aws.sqs.model.SqsMessageBodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@RequiredArgsConstructor
@Component
public class SqsListener {

    private final SqsObjectFacade sqsObjectFacade;
    private final SqsAsyncClient sqsAsyncClient;
    @Setter
    private String queueUrl;
    @Setter
    private SqsMessageBodyHandler messageBodyHandler;

    public void listen() {

        if (queueUrl == null || messageBodyHandler == null) {
            throw new ListenerInitializationException("Queue url or message handler were not initialized");
        }

        var receiveMessageRequest =
            sqsObjectFacade.getReceiveMessageRequest(queueUrl);

        var receiveMessageResponseMono =
            Mono.fromFuture(() -> sqsAsyncClient.receiveMessage(receiveMessageRequest));

        receiveMessageResponseMono
            .repeat()
            .retry()
            .map(ReceiveMessageResponse::messages)
            .map(Flux::fromIterable)
            .flatMap(messageFlux -> messageFlux)
            .subscribe(message -> {

                messageBodyHandler.handle(message.body());

                var deleteMessageRequest =
                    sqsObjectFacade.getDeleteMessageRequest(queueUrl, message.receiptHandle());

                sqsAsyncClient.deleteMessage(deleteMessageRequest);
            });
    }
}

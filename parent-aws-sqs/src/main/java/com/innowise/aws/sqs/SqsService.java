package com.innowise.aws.sqs;

import com.innowise.aws.sqs.exception.SqsProcessingFailedException;
import com.innowise.aws.sqs.model.SqsMessage;
import java.util.List;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public interface SqsService {

  String createQueue(String queueName);
  SendMessageResponse sendMessage(SqsMessage sqsMessage) throws SqsProcessingFailedException;
  List<String> receiveMessages(String url) throws SqsProcessingFailedException;
}

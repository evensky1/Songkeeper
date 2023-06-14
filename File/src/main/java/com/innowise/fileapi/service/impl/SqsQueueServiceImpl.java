package com.innowise.fileapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.aws.sqs.SqsService;
import com.innowise.aws.sqs.model.SqsMessage;
import com.innowise.fileapi.dto.FileInfoDto;
import com.innowise.fileapi.exception.JsonConvertingException;
import com.innowise.fileapi.service.SqsQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsQueueServiceImpl implements SqsQueueService {

    private final SqsService sqsService;
    private final ObjectMapper objectMapper;

    @Override
    public void sendFileInfo(FileInfoDto fileInfo) {

        try {
            var fileInfoJson = objectMapper.writeValueAsString(fileInfo);

            var sqsMessage = new SqsMessage(
                System.getenv("FILE_TO_ENRICHER_QUEUE_URL"),
                "songkeeper.file",
                fileInfoJson
            );

            sqsService.sendMessage(sqsMessage);

        } catch (JsonProcessingException e) {
            throw new JsonConvertingException();
        }
    }
}

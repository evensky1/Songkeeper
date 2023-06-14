package com.innowise.enricher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.aws.sqs.SqsService;
import com.innowise.aws.sqs.exception.SqsProcessingFailedException;
import com.innowise.aws.sqs.model.SqsMessage;
import com.innowise.enricher.dto.spotify.Tracks;
import com.innowise.enricher.dto.converter.TrackInfoConverter;
import com.innowise.enricher.exception.JsonConvertingException;
import com.innowise.enricher.exception.SongMetadataProcessingException;
import com.innowise.enricher.service.TrackInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackInfoServiceImpl implements TrackInfoService {

    private final SqsService sqsService;
    private final ObjectMapper objectMapper;
    private final TrackInfoConverter converter;

    @Override
    public void save(Tracks tracks) {

        var firstSuitableTrack = tracks.items().stream().findFirst()
            .orElseThrow(() -> new SongMetadataProcessingException("Track wasn't found"));

        var trackInfo = converter.from(firstSuitableTrack);

        try {
            var message = new SqsMessage(
                System.getenv("ENRICHER_TO_SONG_QUEUE_URL"),
                "trackinfo",
                objectMapper.writeValueAsString(trackInfo)
            );

            sqsService.sendMessage(message);
        } catch (JsonProcessingException e) {
            throw new JsonConvertingException();
        } catch (SqsProcessingFailedException e) {
            log.error("Unable to send message into queue from TrackInfoService");
            throw new SqsProcessingFailedException(e.getMessage());
        }
    }
}

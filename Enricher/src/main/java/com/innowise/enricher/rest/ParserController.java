package com.innowise.enricher.rest;

import com.innowise.enricher.dto.FileInfoDto;
import com.innowise.enricher.dto.SongCredits;
import com.innowise.enricher.service.MetadataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
@Slf4j
public class ParserController {

    private final MetadataParser metadataParser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongCredits postMessage(@RequestBody FileInfoDto fileInfoDto) {

        return metadataParser.getSongCredits(fileInfoDto);
    }
}

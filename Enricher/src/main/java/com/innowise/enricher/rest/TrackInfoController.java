package com.innowise.enricher.rest;


import com.innowise.enricher.dto.spotify.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/track")
@RequiredArgsConstructor
@Slf4j
public class TrackInfoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void loadTrackInfo(@RequestBody Root root) {
        log.info("{}", root);
    }
}

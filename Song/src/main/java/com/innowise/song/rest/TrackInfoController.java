package com.innowise.song.rest;

import com.innowise.song.dto.TrackInfoDto;
import com.innowise.song.service.TrackInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/track")
public class TrackInfoController {

    private final TrackInfoService trackInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void loadTrackInfo(@RequestBody TrackInfoDto trackInfoDto) {

    }
}

package com.innowise.song.service.impl;

import com.innowise.song.dto.TrackInfoDto;
import com.innowise.song.entity.TrackInfo;
import com.innowise.song.repository.TrackInfoRepository;
import com.innowise.song.service.TrackInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackInfoServiceImpl implements TrackInfoService {

    private final TrackInfoRepository trackInfoRepository;

    @Override
    public void save(TrackInfoDto trackInfoDto) {

        trackInfoRepository.save(
            TrackInfo.builder()
                .track(trackInfoDto.track())
                .album(trackInfoDto.album())
                .artists(trackInfoDto.artists())
                .explicitLyric(trackInfoDto.explicitLyric())
                .releaseDate(trackInfoDto.releaseDate())
                .spotifyUri(trackInfoDto.spotifyUri())
                .build()
        );
    }
}

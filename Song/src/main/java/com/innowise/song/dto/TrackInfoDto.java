package com.innowise.song.dto;

import java.time.LocalDate;
import java.util.List;

public record TrackInfoDto(
    List<String> artists,
    String track,
    String album,
    boolean explicitLyric,
    LocalDate releaseDate,
    String spotifyUri
) {

}

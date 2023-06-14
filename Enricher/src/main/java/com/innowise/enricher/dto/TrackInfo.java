package com.innowise.enricher.dto;

import java.time.LocalDate;
import java.util.List;

public record TrackInfo(
    List<String> artists,
    String track,
    String album,
    boolean explicitLyric,
    LocalDate releaseDate,
    String spotifyUri
) {

}

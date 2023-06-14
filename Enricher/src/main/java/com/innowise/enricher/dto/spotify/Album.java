package com.innowise.enricher.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;

public record Album(
    String albumType,
    ArrayList<Artist> artists,
    @JsonProperty("available_markets") ArrayList<String> availableMarkets,
    String href,
    String id,
    ArrayList<Image> images,
    String name,
    @JsonProperty("release_date") LocalDate releaseDate,
    @JsonProperty("release_date_precision") String releaseDatePrecision,
    @JsonProperty("total_tracks") int totalTracks,
    String type,
    String uri) {

}

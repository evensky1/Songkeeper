package com.innowise.enricher.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.enricher.dto.spotify.Album;
import com.innowise.enricher.dto.spotify.Artist;
import java.util.ArrayList;

public record Item(
    Album album,
    ArrayList<Artist> artists,
    @JsonProperty("available_markets") ArrayList<String> availableMarkets,
    @JsonProperty("disc_number") int discNumber,
    @JsonProperty("duration_ms") int durationInMs,
    boolean explicit,
    String href,
    String id,
    @JsonProperty("is_local") boolean isLocal,
    String name,
    int popularity,
    @JsonProperty("preview_url") String previewUrl,
    @JsonProperty("track_number") int trackNumber,
    String type,
    String uri) {

}

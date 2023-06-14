package com.innowise.enricher.dto.spotify;

import com.innowise.enricher.dto.spotify.Item;
import java.util.ArrayList;

public record Tracks(
    String href,
    ArrayList<Item> items,
    int limit,
    String next,
    int offset,
    int total
) {

}

package com.innowise.enricher.dto.converter;

import com.innowise.enricher.dto.spotify.Artist;
import com.innowise.enricher.dto.spotify.Item;
import com.innowise.enricher.dto.TrackInfo;
import org.springframework.stereotype.Component;

@Component
public class TrackInfoConverter {

    public TrackInfo from(Item item) {

        return new TrackInfo(
            item.artists().stream().map(Artist::name).toList(),
            item.name(),
            item.album().name(),
            item.explicit(),
            item.album().releaseDate(),
            item.uri()
        );
    }
}

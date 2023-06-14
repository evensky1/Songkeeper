package com.innowise.song.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "track_artist", joinColumns = @JoinColumn(name = "track_id"))
    private List<String> artists;

    private String track;
    private String album;
    private boolean explicitLyric;
    private LocalDate releaseDate;
    private String spotifyUri;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackInfo trackInfo)) {
            return false;
        }

        if (explicitLyric != trackInfo.explicitLyric) {
            return false;
        }
        if (!id.equals(trackInfo.id)) {
            return false;
        }
        if (!Objects.equals(artists, trackInfo.artists)) {
            return false;
        }
        if (!track.equals(trackInfo.track)) {
            return false;
        }
        if (!album.equals(trackInfo.album)) {
            return false;
        }
        if (!releaseDate.equals(trackInfo.releaseDate)) {
            return false;
        }
        return spotifyUri.equals(trackInfo.spotifyUri);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + artists.hashCode();
        result = 31 * result + track.hashCode();
        result = 31 * result + album.hashCode();
        result = 31 * result + (explicitLyric ? 1 : 0);
        result = 31 * result + releaseDate.hashCode();
        result = 31 * result + spotifyUri.hashCode();
        return result;
    }
}

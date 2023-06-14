package com.innowise.enricher.service.impl;


import com.innowise.enricher.dto.FileInfoDto;
import com.innowise.enricher.dto.SongCredits;
import com.innowise.enricher.exception.SongMetadataProcessingException;
import com.innowise.enricher.service.MetadataParser;
import com.innowise.enricher.service.SongService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataParserImpl implements MetadataParser {

    private final SongService songService;

    @Override
    public SongCredits getSongCredits(FileInfoDto fileInfoDto) {

        var inputStream = songService.loadSongAsInputStream(fileInfoDto);

        var metadata = fetchSongMetadata(inputStream);

        return parseMetadata(metadata);
    }

    private Metadata fetchSongMetadata(InputStream inputStream) {

        var handler = new BodyContentHandler();
        var metadata = new Metadata();
        var parseContext = new ParseContext();

        var parser = new Mp3Parser();

        try {
            parser.parse(inputStream, handler, metadata, parseContext);
        } catch (IOException | TikaException | SAXException e) {
            log.error("Unable to parse song in MetadataParserImpl");
            throw new SongMetadataProcessingException("Unable to fetch metadata");
        }

        return metadata;
    }

    private SongCredits parseMetadata(Metadata metadata) {

        var title = Optional.ofNullable(metadata.get("title"))
            .or(() -> Optional.ofNullable(metadata.get("dc:title")))
            .orElseThrow(() -> new SongMetadataProcessingException("Title was not found"));

        var artist = Optional.ofNullable(metadata.get("xmpDM:artist"))
            .or(() -> Optional.ofNullable(metadata.get("Author")))
            .or(() -> Optional.ofNullable((metadata.get("meta:author"))))
            .or(() -> Optional.ofNullable(metadata.get("creator")))
            .or(() -> Optional.ofNullable(metadata.get("dc:creator")))
            .orElseThrow(() -> new SongMetadataProcessingException("Artist was not found"));

        return new SongCredits(artist, title);
    }
}

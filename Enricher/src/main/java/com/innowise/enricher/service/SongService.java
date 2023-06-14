package com.innowise.enricher.service;

import com.innowise.enricher.dto.FileInfoDto;
import java.io.InputStream;

public interface SongService {

    InputStream loadSongAsInputStream(FileInfoDto fileInfoDto);
    byte[] loadSongAsBytes(FileInfoDto fileInfoDto);
}

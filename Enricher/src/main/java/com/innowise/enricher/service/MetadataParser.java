package com.innowise.enricher.service;

import com.innowise.enricher.dto.FileInfoDto;
import com.innowise.enricher.dto.SongCredits;

public interface MetadataParser {

    SongCredits getSongCredits(FileInfoDto fileInfoDto);
}

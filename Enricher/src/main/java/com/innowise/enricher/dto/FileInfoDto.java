package com.innowise.enricher.dto;

import java.time.LocalDateTime;

public record FileInfoDto(
    Long id,
    StorageType storageType,
    String key,
    LocalDateTime uploadTime
) {

}

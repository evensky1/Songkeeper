package com.innowise.fileapi.dto;

import com.innowise.fileapi.entity.StorageType;
import java.time.LocalDateTime;

public record FileInfoDto(
    Long id,
    StorageType storageType,
    String key,
    LocalDateTime uploadTime
) {

}

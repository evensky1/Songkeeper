package com.innowise.fileapi.dto.converter;

import com.innowise.fileapi.dto.FileInfoDto;
import com.innowise.fileapi.entity.SongFileInfo;
import org.springframework.stereotype.Component;

@Component
public class FileInfoDtoConverter {

    public FileInfoDto from(SongFileInfo songFileInfo) {

        return new FileInfoDto(
            songFileInfo.getId(),
            songFileInfo.getStorageType(),
            songFileInfo.getKey(),
            songFileInfo.getUploadTime()
        );
    }
}

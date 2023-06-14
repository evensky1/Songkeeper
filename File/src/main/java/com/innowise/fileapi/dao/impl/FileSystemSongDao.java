package com.innowise.fileapi.dao.impl;

import com.innowise.fileapi.dao.SongDao;
import com.innowise.fileapi.dto.UploadResult;
import com.innowise.fileapi.entity.StorageType;
import java.io.IOException;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class FileSystemSongDao implements SongDao {

    @Override
    public UploadResult save(MultipartFile song) {

        var hashedFileName = DigestUtils.md5Hex(song.getOriginalFilename());

        var fileKey = System.getenv("STORAGE_PATH") + "/" + hashedFileName;
        try {
            song.transferTo(Path.of(fileKey));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return new UploadResult(StorageType.FILE, fileKey);
    }
}

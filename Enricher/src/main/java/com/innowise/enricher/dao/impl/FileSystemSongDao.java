package com.innowise.enricher.dao.impl;

import com.innowise.enricher.dao.SongDao;
import com.innowise.enricher.dto.StorageType;
import com.innowise.enricher.exception.FileSystemLoadingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileSystemSongDao implements SongDao {

    @Override
    public byte[] load(String key) {

        try (var fileInputStream = new FileInputStream(key)) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            log.error("Unable to load file", e);
            throw new FileSystemLoadingException();
        }
    }

    @Override
    public InputStream loadAsInputStream(String key) {

        try {
            return new FileInputStream(key);
        } catch (IOException e) {
            log.error("Unable to load file", e);
            throw new FileSystemLoadingException();
        }
    }

    @Override
    public boolean canLoad(StorageType storageType) {
        return storageType == StorageType.FILE;
    }
}

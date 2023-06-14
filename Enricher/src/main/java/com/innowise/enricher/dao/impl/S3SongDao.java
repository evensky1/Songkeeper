package com.innowise.enricher.dao.impl;

import com.innowise.aws.s3.S3Service;
import com.innowise.enricher.dao.SongDao;
import com.innowise.enricher.dto.StorageType;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3SongDao implements SongDao {

    private final S3Service s3Service;

    @Override
    @SneakyThrows
    public byte[] load(String key) {
        return s3Service.getObjectAsByteArray(key);
    }

    @Override
    @SneakyThrows
    public InputStream loadAsInputStream(String key) {
        return s3Service.getObjectAsInputStream(key);
    }

    @Override
    public boolean canLoad(StorageType storageType) {
        return storageType == StorageType.BUCKET;
    }
}

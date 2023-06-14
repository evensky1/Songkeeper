package com.innowise.enricher.dao;

import com.innowise.enricher.dto.StorageType;
import java.io.InputStream;

public interface SongDao {

    byte[] load(String key);
    InputStream loadAsInputStream(String key);
    boolean canLoad(StorageType storageType);
}

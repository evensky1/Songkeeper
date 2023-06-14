package com.innowise.enricher.service.impl;

import com.innowise.enricher.dao.SongDao;
import com.innowise.enricher.dto.FileInfoDto;
import com.innowise.enricher.exception.UnknownStorageTypeException;
import com.innowise.enricher.service.SongService;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final List<SongDao> songDaoList;


    @Override
    public byte[] loadSongAsBytes(FileInfoDto fileInfoDto) {

        return songDaoList.stream()
            .filter(songDao -> songDao.canLoad(fileInfoDto.storageType()))
            .findFirst()
            .orElseThrow(UnknownStorageTypeException::new)
            .load(fileInfoDto.key());
    }

    @Override
    public InputStream loadSongAsInputStream(FileInfoDto fileInfoDto) {

        return songDaoList.stream()
            .filter(songDao -> songDao.canLoad(fileInfoDto.storageType()))
            .findFirst()
            .orElseThrow(UnknownStorageTypeException::new)
            .loadAsInputStream(fileInfoDto.key());
    }
}

package com.innowise.fileapi.service.impl;

import com.innowise.fileapi.dao.SongDao;
import com.innowise.fileapi.dto.FileInfoDto;
import com.innowise.fileapi.dto.converter.FileInfoDtoConverter;
import com.innowise.fileapi.entity.SongFileInfo;
import com.innowise.fileapi.repository.SongFileInfoRepository;
import com.innowise.fileapi.service.SongService;
import com.innowise.fileapi.service.SqsQueueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongServiceImpl implements SongService {

    private final SongFileInfoRepository songFileInfoRepository;
    private final SongDao s3SongDao;
    @Lazy
    private final SongDao fileSystemSongDao;
    private final SqsQueueService sqsQueueService;
    private final FileInfoDtoConverter converter;

    @Override
    @Retryable
    @Transactional
    public FileInfoDto save(MultipartFile song) {
        return saveFile(s3SongDao, song);
    }

    @Recover
    public FileInfoDto saveToFileSystem(Exception e, MultipartFile song) {
        log.error(e.getMessage(), e);

        return saveFile(fileSystemSongDao, song);
    }

    private FileInfoDto saveFile(SongDao songDao, MultipartFile song) {

        var uploadRes = songDao.save(song);

        var fileInfo = SongFileInfo.builder()
            .key(uploadRes.key())
            .storageType(uploadRes.storageType())
            .fileName(song.getOriginalFilename())
            .build();

        var savedFileInfo = songFileInfoRepository.save(fileInfo);

        sqsQueueService.sendFileInfo(converter.from(savedFileInfo));

        return converter.from(savedFileInfo);
    }
}

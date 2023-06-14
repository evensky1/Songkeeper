package com.innowise.fileapi.dao.impl;

import com.innowise.aws.s3.S3Service;
import com.innowise.aws.s3.model.S3ObjectPath;
import com.innowise.fileapi.dao.SongDao;
import com.innowise.fileapi.dto.UploadResult;
import com.innowise.fileapi.entity.StorageType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3SongDao implements SongDao {

    private final S3Service s3Service;

    @Override
    @SneakyThrows
    public UploadResult save(MultipartFile song) {

        var hashedFileName = DigestUtils.md5Hex(song.getOriginalFilename());

        var s3Obj = new S3ObjectPath(hashedFileName, "songs");
        s3Service.putObject(s3Obj, song.getInputStream());

        return new UploadResult(StorageType.BUCKET, s3Obj.getKey());
    }
}

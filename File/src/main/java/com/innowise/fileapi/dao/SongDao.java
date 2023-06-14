package com.innowise.fileapi.dao;

import com.innowise.fileapi.dto.UploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface SongDao {

    UploadResult save(MultipartFile song);
}

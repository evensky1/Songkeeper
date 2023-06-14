package com.innowise.fileapi.service;

import com.innowise.fileapi.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface SongService {

    FileInfoDto save(MultipartFile song);
}

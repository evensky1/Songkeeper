package com.innowise.fileapi.service;

import com.innowise.fileapi.dto.FileInfoDto;

public interface SqsQueueService {

    void sendFileInfo(FileInfoDto fileInfo);
}

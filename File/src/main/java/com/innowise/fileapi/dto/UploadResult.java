package com.innowise.fileapi.dto;

import com.innowise.fileapi.entity.StorageType;

public record UploadResult (
    StorageType storageType,
    String key
){

}

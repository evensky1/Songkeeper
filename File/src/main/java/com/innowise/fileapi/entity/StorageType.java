package com.innowise.fileapi.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageType {
    BUCKET("bucket"),
    FILE("file");

    private final String name;
}

package com.innowise.enricher.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageType {
    BUCKET("bucket"),
    FILE("file");

    private final String name;
}

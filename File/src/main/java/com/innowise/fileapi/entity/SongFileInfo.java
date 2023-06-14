package com.innowise.fileapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SongFileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    private StorageType storageType;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "upload_time")
    @CreationTimestamp
    private LocalDateTime uploadTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SongFileInfo that)) {
            return false;
        }

        if (!id.equals(that.id)) {
            return false;
        }
        if (storageType != that.storageType) {
            return false;
        }
        if (!key.equals(that.key)) {
            return false;
        }
        return uploadTime.equals(that.uploadTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + storageType.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + uploadTime.hashCode();
        return result;
    }
}

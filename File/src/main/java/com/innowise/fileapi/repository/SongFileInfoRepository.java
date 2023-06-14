package com.innowise.fileapi.repository;

import com.innowise.fileapi.entity.SongFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFileInfoRepository extends JpaRepository<SongFileInfo, Long> {

}

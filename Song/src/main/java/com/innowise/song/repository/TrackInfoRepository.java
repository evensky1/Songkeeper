package com.innowise.song.repository;

import com.innowise.song.entity.TrackInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackInfoRepository extends JpaRepository<TrackInfo, Long> {

}

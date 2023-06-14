package com.innowise.fileapi.rest;

import com.innowise.fileapi.dto.FileInfoDto;
import com.innowise.fileapi.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/song")
public class SongController {

    private final SongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileInfoDto uploadFile(@RequestParam(name = "song") MultipartFile song) {

        return songService.save(song);
    }
}

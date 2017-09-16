package io.euntak.controller;

import io.euntak.domain.FileInfo;
import io.euntak.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController("/api/files")
public class FileRestController {

    @Autowired
    FileService fileService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Map<String, String> getAllfiles() {
        return fileService.getAllFiles();
    }

    @PostMapping(headers = ("content-type=multipart/*"))
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile[] files) {
        HttpHeaders headers = new HttpHeaders(); // Http Headers

        if (files.length == 0) {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fileService.uploadMultiFiles(files), headers, HttpStatus.OK);

    }
}

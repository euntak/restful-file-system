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

@RestController
@RequestMapping ("/api/files")
public class FileRestController {

    FileService fileService;

    @Autowired
    public FileRestController(FileService fileService) {

        this.fileService = fileService;
    }


    @GetMapping
    @ResponseStatus (HttpStatus.OK)
    public ResponseEntity<?> getAllfiles() {

        HttpHeaders headers = new HttpHeaders(); // Http Headers
        headers.setContentType(MediaType.APPLICATION_JSON);

//        Boolean isSuccess = (boolean) fileService.getAllFiles().get("isSuccess");
//        System.err.println("isSuccess : " + fileService.getAllFiles());

        return new ResponseEntity<>(fileService.getAllFiles(), headers, HttpStatus.OK);
    }

    @PostMapping (headers = ("content-type=multipart/*"))
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam ("file") MultipartFile[] files) {

        HttpHeaders headers = new HttpHeaders();

        if (files.length == 0) {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(fileService.uploadMultiFiles(files), headers, HttpStatus.OK);

    }

    @PutMapping (value = "/{fileId}", headers = "content-type=multipart/*")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long fileId,
                                    @RequestParam ("file") MultipartFile[] files) {

        HttpHeaders headers = new HttpHeaders();

        if (files.length == 0 || files.length > 1) {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fileService.updateFile(fileId, files[0]), headers, HttpStatus.OK);
    }


    @DeleteMapping (value = "/{fileId}")
    public ResponseEntity<?> delete(@PathVariable Long fileId) {

        System.err.println("Delete fileId : " + fileId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

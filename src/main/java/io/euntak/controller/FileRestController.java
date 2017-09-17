package io.euntak.controller;

import io.euntak.domain.FileInfo;
import io.euntak.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/api/files")
public class FileRestController {

    FileService fileService;

    @Autowired
    public FileRestController(FileService fileService) {

        this.fileService = fileService;
    }

    @GetMapping (value = "/{fileId:[\\d]+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable Long fileId) throws IOException {

        File file = null;
        String mimeType = null;
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> message = new HashMap<>();
        message.put("location", "/api/files/{fileId}");

        FileInfo selectedFile = fileService.getFile(fileId);

        if (selectedFile == null) {
            message.put("message", "Failure DB get FileInfo");
            message.put("success", false);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            mimeType = URLConnection.guessContentTypeFromName(selectedFile.getContentType());
            file = new File(selectedFile.getSaveFileName());
        }

        if (file != null && ! file.exists()) {
            message.put("message", "File NOT Found");
            message.put("success", false);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        byte[] document = FileCopyUtils.copyToByteArray(file);
        headers.setContentType(MediaType.valueOf(mimeType));
        headers.set("Content-Disposition", "attachment; filename=" + file.getName());
        headers.setContentLength(document.length);

        return new ResponseEntity(document, headers, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAllFiles(@RequestParam (required = false) String type) {

        if (type != null) {
            return new ResponseEntity<>(fileService.getFiles(type), HttpStatus.OK);
        }

        return new ResponseEntity<>(fileService.getAllFiles(), HttpStatus.OK);
    }

    @PostMapping (headers = ("content-type=multipart/*"))
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam ("file") MultipartFile[] files) {

        if (files.length == 0) {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fileService.uploadMultiFiles(files), HttpStatus.OK);

    }

    @PutMapping (value = "/{fileId:[\\d]+}", headers = "content-type=multipart/*")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long fileId,
                                    @RequestParam ("file") MultipartFile[] files) {

        if (files.length == 0 || files.length > 1) {
            return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(fileService.updateFile(fileId, files[0]), HttpStatus.OK);
    }


    @DeleteMapping (value = "/{fileId:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long fileId) {

        if (fileId <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(fileService.deleteFile(fileId), HttpStatus.OK);
    }
}

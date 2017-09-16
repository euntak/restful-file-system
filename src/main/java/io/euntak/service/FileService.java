package io.euntak.service;

import io.euntak.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    Map<String, String> getAllFiles();

    FileInfo writeFile(MultipartFile file);

    FileInfo removeFile(String removeFileName);


    Map<String, Object> updateFile(Long fileId, MultipartFile file);

    Map<String, Object> uploadMultiFiles(MultipartFile[] files);
}

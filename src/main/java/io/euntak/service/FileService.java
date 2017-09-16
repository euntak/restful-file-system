package io.euntak.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    Map<String, String> getAllFiles();

    Map<String, Object> uploadMultiFiles(MultipartFile[] files);
}

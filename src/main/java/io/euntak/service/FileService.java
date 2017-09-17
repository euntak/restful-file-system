package io.euntak.service;

import io.euntak.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    Map<String, Object> getAllFiles();

    Map<String, Object> getFiles(String type);

    FileInfo getFile(Long fileId);

    FileInfo writeFile(MultipartFile file);

    boolean removeFile(FileInfo fileIndo);

    Map<String, Object> deleteFile(Long fileId);

    Map<String, Object> updateFile(Long fileId, MultipartFile file);

    Map<String, Object> uploadMultiFiles(MultipartFile[] files);
}

package io.euntak.dao;

import io.euntak.domain.FileInfo;

import java.util.List;

public interface FileDao {
    FileInfo selectFileById(Long fileId);

    int updateFileById(Long fileId, FileInfo fileInfo);

    Long insert(FileInfo f);

    int delete(Long fileId);

    List<FileInfo> selectAllFiles();

    List<FileInfo> selectFilesByFilter(String type);
}

package io.euntak.dao;

import io.euntak.domain.FileInfo;

import java.util.List;

public interface FileDao {
    FileInfo selectFileById(Long fileId);

    int updateFileById(Long fileId, FileInfo fileInfo);

    Long insert(FileInfo f);

    List<FileInfo> selectAllFiles();
}

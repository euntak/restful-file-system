package io.euntak.dao;

import io.euntak.domain.FileInfo;

public interface FileDao {
    FileInfo selectFileById(Long fileId);
    Long insert(FileInfo f);
}

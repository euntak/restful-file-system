package io.euntak.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfo {
    private long id;
    private long userId;

    private String fileName;
    private String saveFileName;

    private long fileLength;
    private String contentType;
    private int deleteFlag;

    private String createDate;
    private String modifyDate;
}

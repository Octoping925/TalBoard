package com.talmo.talboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FileUpload {
    private String fileName;
    private String fileOriginalName;
    private String fileDownloadUri;
    private String fileExt;
    private long size;

    public FileUpload(String fileName, String fileOriginalName, String fileExt, String fileDownloadUri) {
        this.fileName = fileName;
        this.fileOriginalName = fileOriginalName;
        this.fileExt = fileExt;
        this.fileDownloadUri = fileDownloadUri;
    }
}

package com.jong1.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}

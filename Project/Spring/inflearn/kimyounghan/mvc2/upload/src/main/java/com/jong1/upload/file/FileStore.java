package com.jong1.upload.file;

import com.jong1.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResults = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                storeFileResults.add(storeFile(multipartFile));
            }
        }
        return storeFileResults;
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()) {
            return null;
        }

        // 원본 파일명
        String originalFileName = multipartFile.getOriginalFilename();

        // 서버에 저장되는 파일명
        String storeFileName = createStoreFileName(originalFileName);

        // 서버 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFileName, storeFileName);
    }

    private String createStoreFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + extractedExt(originalFileName);
    }

    private String extractedExt(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }


}

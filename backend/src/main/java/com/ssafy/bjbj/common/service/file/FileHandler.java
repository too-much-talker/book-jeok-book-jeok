package com.ssafy.bjbj.common.service.file;

import com.ssafy.bjbj.common.entity.file.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileHandler {
    public List<FileInfo> parseFileInfo(List<MultipartFile> multipartFiles) throws IOException {

        List<FileInfo> fileInfos = new ArrayList<>();

        if (multipartFiles.isEmpty()) {
            return fileInfos;
        }

        String absolutePath = new File("").getAbsolutePath() + File.separator;

        String path ="images" + File.separator + LocalDate.now();
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {

            if (!multipartFile.isEmpty()) {

                String originalFilename = multipartFile.getOriginalFilename();
                int indexOf = originalFilename.lastIndexOf(".");
                String encodedFileName = UUID.randomUUID() + originalFilename.substring(indexOf);

                FileInfo fileInfo = FileInfo.builder()
                        .originFileName(originalFilename)
                        .encodedFileName(encodedFileName)
                        .savedPath(path)
                        .isDeleted(false)
                        .build();

                fileInfos.add(fileInfo);

                file = new File(absolutePath + path);
                multipartFile.transferTo(new File(file, encodedFileName));

            }
        }

        return fileInfos;
    }
}

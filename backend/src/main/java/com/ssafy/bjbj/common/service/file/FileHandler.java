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

        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "images/" + LocalDate.now();
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {

            if (!multipartFile.isEmpty()) {

                int indexOf = multipartFile.getOriginalFilename().lastIndexOf(".");

                String newFileName = UUID.randomUUID() + multipartFile.getOriginalFilename().substring(indexOf);

                FileInfo fileInfo = FileInfo.builder()
                        .originFileName(multipartFile.getOriginalFilename())
                        .encodedFileName(newFileName)
                        .savedPath(path + File.separator + newFileName)
                        .isDeleted(false)
                        .build();

                fileInfos.add(fileInfo);

                file = new File(absolutePath + path);
                multipartFile.transferTo(new File(file, newFileName));

            }
        }

        return fileInfos;
    }
}

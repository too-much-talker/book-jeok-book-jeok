package com.ssafy.bjbj.common.service.file;

import com.ssafy.bjbj.common.entity.file.FileInfo;
import com.ssafy.bjbj.common.repository.file.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FileInfoServiceImpl implements FileInfoService{

    private final FileInfoRepository fileInfoRepository;

    private final FileHandler fileHandler;

    @Transactional
    @Override
    public void registerFileInfo(Long rootSeq, List<MultipartFile> files) throws IOException {
        List<FileInfo> fileInfos = fileHandler.parseFileInfo(files);

        if (!fileInfos.isEmpty()) {
            for (FileInfo fileInfo : fileInfos) {
                fileInfo.setRootSeq(rootSeq);
                System.out.println("fileInfo = " + fileInfo);
                fileInfoRepository.save(fileInfo);
            }
        }
    }

    @Override
    public List<String> findAllFileInfoByReadingGroupBoardSeq(Long rootSeq) {
        List<FileInfo> fileInfos = fileInfoRepository.findAllByRootSeq(rootSeq);
        System.out.println("fileInfos = " + fileInfos);

        List<String> paths = new ArrayList<>();

        if (fileInfos != null) {
            for (FileInfo fileInfo : fileInfos) {
                if (!fileInfo.isDeleted()) {
                    paths.add(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedFileName());
                }
            }
        }

        return paths;
    }

    @Transactional
    @Override
    public void updateFileInfo(Long rootSeq, List<MultipartFile> files) throws IOException {

        List<FileInfo> fileInfos = fileInfoRepository.findAllByRootSeq(rootSeq);

        if (!fileInfos.isEmpty()) {
            for (FileInfo fileInfo : fileInfos) {
                File file = new File(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedFileName());

                if (file.exists()) {
                    file.delete();
                }

                fileInfo.delete();
            }
        }

        List<FileInfo> updateFileInfos = fileHandler.parseFileInfo(files);

        if (!updateFileInfos.isEmpty()) {
            for (FileInfo fileInfo : updateFileInfos) {
                fileInfo.setRootSeq(rootSeq);
                fileInfoRepository.save(fileInfo);
            }
        }
    }

    @Transactional
    @Override
    public void deleteFileInfo(Long rootSeq, Long memberSeq) {

        List<FileInfo> fileInfos = fileInfoRepository.findAllByRootSeq(rootSeq);

        if (!fileInfos.isEmpty()) {
            for (FileInfo fileInfo : fileInfos) {
                File file = new File(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedFileName());

                if (file.exists()) {
                    file.delete();
                }

                fileInfo.delete();
            }
        }
    }
}

package com.ssafy.bjbj.common.service.file;

import com.ssafy.bjbj.common.entity.file.FileInfo;
import com.ssafy.bjbj.common.repository.file.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FileInfoServiceImpl implements FileInfoService{

    private final FileInfoRepository fileInfoRepository;

    @Override
    public List<String> findAllFileInfoByReadingGroupBoardSeq(Long readingGroupBoardSeq) {
        List<FileInfo> fileInfos = fileInfoRepository.findAllByRootSeq(readingGroupBoardSeq);

        List<String> paths = new ArrayList<>();

        if (fileInfos != null) {
            for (FileInfo fileInfo : fileInfos) {
                paths.add(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedFileName());
            }
        }

        return paths;
    }

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

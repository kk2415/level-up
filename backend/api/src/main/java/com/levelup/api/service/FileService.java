package com.levelup.api.service;


import com.levelup.core.domain.file.File;
import com.levelup.core.domain.file.UploadFile;
import com.levelup.core.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 생성
     * */
    public Long save(Object object, UploadFile uploadFile) {
        File file = File.createFile(object, uploadFile);

        fileRepository.save(file);
        return file.getId();
    }

    public List<Long> save(Object object, List<UploadFile> uploadFile) {
        List<Long> ids = new ArrayList<>();

        for (UploadFile file : uploadFile) {
            Long id = save(object, file);
            ids.add(id);
        }

        return ids;
    }

    /**
     * 조회
     * */
    public File getById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("파일이 존재하지 않습니다."));
    }

    /**
     * 삭제
     * */
    public void delete(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("파일이 존재하지 않습니다."));

        fileRepository.delete(file);
    }
}

package com.together.levelup.service;

import com.together.levelup.domain.file.UploadFile;
import com.together.levelup.domain.file.File;
import com.together.levelup.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 생성
     * */
    public Long create(Object object, UploadFile uploadFile) {
        File file = File.createFile(object, uploadFile);

        fileRepository.save(file);
        return file.getId();
    }

    /**
     * 조회
     * */
    public File findById(Long id) {
        return fileRepository.findById(id);
    }

    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public List<File> findByPostId(Long postId) {
        return fileRepository.findByPostId(postId);
    }

    public List<File> findByNoticeId(Long noticeId) {
        return fileRepository.findByNoticeId(noticeId);
    }

    public List<File> findByChannelNoticeId(Long channelNoticeId) {
        return fileRepository.findByChannelNoticeId(channelNoticeId);
    }

    public List<File> findByQnaId(Long qnaId) {
        return fileRepository.findByQnaId(qnaId);
    }


    /**
     * 삭제
     * */
    public void delete(Long id) {
        fileRepository.delete(id);
    }

    public void deleteByPostId(Long postId) {
        List<File> findPostFiles = fileRepository.findByPostId(postId);

        for (File findPostFile : findPostFiles) {
            fileRepository.delete(findPostFile.getId());
        }
    }

}

package com.levelup.core.repository.file;


import com.levelup.core.domain.file.File;

import java.util.List;

public interface FileRepository {

    void save(File file);
    File findById(Long id);
    List<File> findAll();
    List<File> findByPostId(Long postId);
    List<File> findByNoticeId(Long noticeId);
    List<File> findByChannelNoticeId(Long channelNoticeId);
    List<File> findByQnaId(Long qnaId);
    void delete(Long id);

}

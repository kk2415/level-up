package com.together.levelup.repository.file;

import com.together.levelup.domain.file.File;

import java.util.List;

public interface FileRepository {

    public void save(File file);
    public File findById(Long id);
    public List<File> findAll();
    public List<File> findByPostId(Long postId);
    public List<File> findByNoticeId(Long noticeId);
    public List<File> findByChannelNoticeId(Long channelNoticeId);
    public List<File> findByQnaId(Long qnaId);
    public void delete(Long id);

}

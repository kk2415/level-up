package com.together.levelup.repository.file;

import com.together.levelup.domain.file.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaFileRepository implements FileRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(File file) {
        em.persist(file);
    }


    /**
     * 조회
     * */
    @Override
    public File findById(Long id) {
        return em.find(File.class, id);
    }

    @Override
    public List<File> findAll() {
        return em.createQuery("select f from File f", File.class)
                .getResultList();
    }

    @Override
    public List<File> findByPostId(Long postId) {
        String query = "select f from File f join fetch f.post p where p.id = :postId";

        return em.createQuery(query, File.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public List<File> findByNoticeId(Long noticeId) {
        String query = "select f from File f join fetch f.notice n where n.id = :noticeId";

        return em.createQuery(query, File.class)
                .setParameter("noticeId", noticeId)
                .getResultList();
    }

    @Override
    public List<File> findByChannelNoticeId(Long channelNoticeId) {
        String query = "select f from File f join fetch f.channelNotice cn where cn.id = :channelNoticeId";

        return em.createQuery(query, File.class)
                .setParameter("channelNoticeId", channelNoticeId)
                .getResultList();
    }

    @Override
    public List<File> findByQnaId(Long qnaId) {
        String query = "select f from File f join fetch f.qna q where q.id = :qnaId";

        return em.createQuery(query, File.class)
                .setParameter("qnaId", qnaId)
                .getResultList();
    }

    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        File findFile = findById(id);
        em.remove(findFile);
    }

}

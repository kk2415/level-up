package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChannelRepository implements ChannelRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(Channel channel) {
        em.persist(channel);
    }


    /**
     * 조회
     * */
    @Override
    public Channel findById(Long id) {
        return em.find(Channel.class, id);
    }

    @Override
    public List<Channel> findByName(String name) {
        String query = "select ch from Channel ch where ch.name = :name";

        return em.createQuery(query, Channel.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Channel> findByMemberId(Long memberId) {
        String query = "select ch from Channel ch " +
                "inner join ch.channelMembers chm " +
                "inner join chm.member m " +
                "where m.id = :memberId";

        return em.createQuery(query, Channel.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Channel findByArticleId(Long articleId) {
        String query = "select ch from Channel ch " +
                "join ChannelPost cp on ch.id = cp.channel.id " +
                "where cp.articleId = :articleId";

        return em.createQuery(query, Channel.class)
                .setParameter("articleId", articleId)
                .getSingleResult();
    }

    @Override
    public List<Channel> findByCategory(ChannelCategory category) {
        String query = "select ch from Channel ch where ch.category = :category order by ch.id desc";

        return em.createQuery(query, Channel.class)
                .setParameter("category", category)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<Channel> findAll() {
        return em.createQuery("select ch from Channel ch", Channel.class)
                .getResultList();
    }

    @Override
    public List<Channel> findAll(int start, int end) {
        return em.createQuery("select ch from Channel ch", Channel.class)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Channel findChannl = findById(id);
        em.remove(findChannl);
    }

}

package com.together.community.repository.channel;

import com.together.community.domain.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChannelRepository implements ChannelRepository {

    private final EntityManager em;

    @Override
    public void save(Channel channel) {
        em.persist(channel);
    }

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
    public List<Channel> findAll() {
        return em.createQuery("select ch from Channel ch", Channel.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        Channel findChannl = findById(id);
        em.remove(findChannl);
    }

}

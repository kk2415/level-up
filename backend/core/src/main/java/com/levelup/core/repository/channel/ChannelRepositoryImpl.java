package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.channel.ChannelCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelCustomRepository {

    private final EntityManager em;

    @Override
    public List<Channel> findByCategory(ChannelCategory category) {
        String query = "select ch from Channel ch " +
                "join fetch ch.channelMembers chm " +
                "join fetch chm.member m " +
                "join fetch m.emailAuth ea " +
                "where ch.category = :category order by ch.id desc";

        return em.createQuery(query, Channel.class)
                .setParameter("category", category)
                .setMaxResults(10)
                .getResultList();
    }
}

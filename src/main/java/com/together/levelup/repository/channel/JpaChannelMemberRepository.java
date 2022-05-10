package com.together.levelup.repository.channel;

import com.together.levelup.domain.channel.ChannelMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChannelMemberRepository implements ChannelMemberRepository {

    private final EntityManager em;

    /**
     * 조회
     * */
    @Override
    public Long save(ChannelMember channelMember) {
        em.persist(channelMember);
        return  channelMember.getId();
    }


    /**
     * 조회
     * */
    @Override
    public List<ChannelMember> findByChannelAndMember(Long channelId, Long memberId) {
        String query = "select cm from ChannelMember cm where " +
                "cm.channel.id = :channelId and cm.member.id =:memberId";

        return em.createQuery(query, ChannelMember.class)
                .setParameter("channelId", channelId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<ChannelMember> findByChannelAndWaitingMember(Long channelId, Long memberId) {
        String query = "select cm from ChannelMember cm where " +
                "cm.channel.id = :channelId and cm.waitingMember.id =:memberId";

        return em.createQuery(query, ChannelMember.class)
                .setParameter("channelId", channelId)
                .setParameter("memberId", memberId)
                .getResultList();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        em.remove(em.find(ChannelMember.class, id));
    }

}

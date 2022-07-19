package com.levelup.core.repository.channel;

import com.levelup.core.domain.channel.ChannelMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class JpaChannelMemberRepository {

    private final EntityManager em;

    /**
     * 조회
     * */
    public Long save(ChannelMember channelMember) {
        em.persist(channelMember);
        return  channelMember.getId();
    }


    /**
     * 조회
     * */
    public List<ChannelMember> findByChannelAndMember(Long channelId, Long memberId) {
        String query = "select cm from ChannelMember cm where cm.channel.id = :channelId and cm.member.id =:memberId";

        return em.createQuery(query, ChannelMember.class)
                .setParameter("channelId", channelId)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<ChannelMember> findByChannelAndWaitingMember(Long channelId, Long memberId) {
        String query = "select cm from ChannelMember cm where cm.channel.id = :channelId and cm.waitingMember.id =:memberId";

        return em.createQuery(query, ChannelMember.class)
                .setParameter("channelId", channelId)
                .setParameter("memberId", memberId)
                .getResultList();
    }


    /**
     * 삭제
     * */
    public void delete(Long id) {
        em.remove(em.find(ChannelMember.class, id));
    }

}

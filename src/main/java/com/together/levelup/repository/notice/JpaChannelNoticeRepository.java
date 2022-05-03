package com.together.levelup.repository.notice;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.levelup.domain.notice.ChannelNotice;
import com.together.levelup.domain.notice.QChannelNotice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaChannelNoticeRepository implements ChannelNoticeRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(ChannelNotice channelNotice) {
        em.persist(channelNotice);
    }

    @Override
    public void saveAll(List<ChannelNotice> channelNotices) {
        for (ChannelNotice channelNotice : channelNotices) {
            em.persist(channelNotice);
        }
    }

    /**
     * 조회
     * */
    @Override
    public ChannelNotice findById(Long id) {
        return em.find(ChannelNotice.class, id);
    }

    @Override
    public List<ChannelNotice> findByChannelId(Long channelId, int page) {
        int firstPage = (page - 1) * 5; //0, 5, 10, 15
        int lastPage = page * 5; //5, 10, 15, 20

        String query = "select cn from ChannelNotice cn join fetch cn.channel c where c.id = :channelId";

        return em.createQuery(query, ChannelNotice.class)
                .setParameter("channelId", channelId)
                .setFirstResult(firstPage)
                .setMaxResults(lastPage)
                .getResultList();
    }

    @Override
    public List<ChannelNotice> findByMemberId(Long memberId) {
        String query = "select cn from ChannelNotice cn inner join cn.channel c where c.member.id = :memberId";

        return em.createQuery(query, ChannelNotice.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<ChannelNotice> findAll() {
        return em.createQuery("select cn from ChannelNotice cn", ChannelNotice.class)
                .getResultList();
    }

    @Override
    public ChannelNotice findNextPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QChannelNotice.channelNotice)
                .from(QChannelNotice.channelNotice)
                .where(QChannelNotice.channelNotice.id.gt(id))
                .orderBy(QChannelNotice.channelNotice.id.asc())
                .fetchFirst();
    }

    @Override
    public ChannelNotice findPrevPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QChannelNotice.channelNotice)
                .from(QChannelNotice.channelNotice)
                .where(QChannelNotice.channelNotice.id.lt(id))
                .orderBy(QChannelNotice.channelNotice.id.asc())
                .fetchFirst();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        ChannelNotice findChannelNotice = findById(id);
        em.remove(findChannelNotice);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            ChannelNotice findChannelNotice = findById(id);
            em.remove(findChannelNotice);
        }
    }
}

package com.levelup.core.repository.notice;

import com.levelup.core.domain.notice.Notice;
import com.levelup.core.domain.notice.QNotice;
import com.levelup.core.dto.post.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaNoticeRepository implements NoticeRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(Notice notice) {
        em.persist(notice);
    }


    /**
     * 조회
     * */
    @Override
    public Notice findById(Long id) {
        return em.find(Notice.class, id);
    }

    @Override
    public List<Notice> findAll() {
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    @Override
    public List<Notice> findAll(Long page, SearchCondition postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<Notice> query = queryFactory.select(QNotice.notice)
                .from(QNotice.notice)
                .where(equalQuery(postSearch))
                .orderBy(QNotice.notice.createdDate.desc());

        if (page != null) {
            Long firstPage = (page - 1) * 10; //0, 10, 20, 30

            return query.offset(firstPage).limit(10).fetch();
        }
        return query.fetch();
    }

    private BooleanExpression equalQuery(SearchCondition postSearch) {
        if (postSearch == null || postSearch.getField() == null || postSearch.getQuery() == null) {
            return null;
        }

        if (postSearch.getField().equals("title")) {
            return QNotice.notice.title.contains(postSearch.getQuery());
        }
        return QNotice.notice.writer.contains(postSearch.getQuery());
    }

    @Override
    public List<Notice> findByMemberId(Long memberId) {
        String query = "select n from Notice n join n.member m where m.id = :memberId "
                + "order by n.createdDate desc";

        return em.createQuery(query, Notice.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public Notice findNextPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QNotice.notice)
                .from(QNotice.notice)
                .where(QNotice.notice.id.gt(id))
                .orderBy(QNotice.notice.id.asc())
                .fetchFirst();
    }

    @Override
    public Notice findPrevPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QNotice.notice)
                .from(QNotice.notice)
                .where(QNotice.notice.id.lt(id))
                .orderBy(QNotice.notice.id.asc())
                .fetchFirst();
    }

    @Override
    public Long count(Long page, SearchCondition postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        JPAQuery<Long> query = queryFactory.select(QNotice.notice.count())
                .from(QNotice.notice)
                .where(equalQuery(postSearch));

        if (page != null) {
            Long firstPage = (page - 1) * 10; //0, 10, 20, 30

            return query.offset(firstPage).limit(10).fetchFirst();
        }
        return query.fetchFirst();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Notice findNotice = findById(id);
        em.remove(findNotice);
    }

}

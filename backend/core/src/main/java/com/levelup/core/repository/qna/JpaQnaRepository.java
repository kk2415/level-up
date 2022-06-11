package com.levelup.core.repository.qna;

import com.levelup.core.domain.qna.QQna;
import com.levelup.core.domain.qna.Qna;
import com.levelup.core.dto.post.SearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaQnaRepository implements QnaRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public void save(Qna qna) {
        em.persist(qna);
    }


    /**
     * 조회
     * */
    @Override
    public Qna findById(Long id) {
        return em.find(Qna.class, id);
    }

    @Override
    public List<Qna> findAll(int page, SearchCondition postSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        int firstPage = (page - 1) * 10; //0, 10, 20, 30

        return queryFactory.select(QQna.qna)
                .from(QQna.qna)
                .where(equalQuery(postSearch))
                .orderBy(QQna.qna.createdDate.desc())
                .offset(firstPage)
                .limit(10)
                .fetch();
    }

    private BooleanExpression equalQuery(SearchCondition postSearch) {
        if (postSearch == null || postSearch.getField() == null || postSearch.getQuery() == null) {
            return null;
        }

        if (postSearch.getField().equals("title")) {
            return QQna.qna.title.contains(postSearch.getQuery());
        }
        return QQna.qna.writer.contains(postSearch.getQuery());
    }

    @Override
    public List<Qna> findByMemberId(Long memberId) {
        String query = "select q from Qna q join fetch q.member m where m.id = :memberId "
                + "order by q.createdDate desc";

        return em.createQuery(query, Qna.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Qna> findAll() {
        return em.createQuery("select q from Qna q", Qna.class)
                .getResultList();
    }

    @Override
    public Qna findNextPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QQna.qna)
                .from(QQna.qna)
                .where(QQna.qna.id.gt(id))
                .orderBy(QQna.qna.id.asc())
                .fetchFirst();
    }

    @Override
    public Qna findPrevPage(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(QQna.qna)
                .from(QQna.qna)
                .where(QQna.qna.id.lt(id))
                .orderBy(QQna.qna.id.asc())
                .fetchFirst();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Qna findQna = findById(id);
        em.remove(findQna);
    }

}

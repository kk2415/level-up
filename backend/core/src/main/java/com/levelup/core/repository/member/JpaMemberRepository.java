package com.levelup.core.repository.member;

import com.levelup.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    /**
     * 생성
     * */
    @Override
    public Long save(Member member) {
        em.persist(member);

        return member.getId();
    }



    /**
     * 조회
     * */
    @Override
    public Member findById(Long id) {
        return em.createQuery("select m from Member m " +
                        "join fetch m.emailAuth e " +
                        "where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Member findByEmail(String email) {
        return em.createQuery("select m from Member m " +
                        "join fetch m.emailAuth e " +
                        "where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Member findByEmailWithOutEmailAuth(String email) {
        return em.createQuery("select m from Member m " +
                        "where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public Member findByEmailAndPassword(String email, String password) {
        String query = "select m from Member m where m.email = :email and m.password = :password";

        return em.createQuery(query, Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }

    @Override
    public List<Member> findByChannelId(Long channelId) {
        String query = "select cm.member from ChannelMember cm join cm.channel c where c.id = :channelId";

        return em.createQuery(query, Member.class)
                .setParameter("channelId", channelId)
                .getResultList();
    }

    @Override
    public List<Member> findByChannelId(Long channelId, int page, int count) {
        int firstPage = (page - 1) * count; //0, 5, 10, 15
        int lastPage = page * count; //5, 10, 15, 20

        String query = "select cm.member from ChannelMember cm join cm.channel c where c.id = :channelId";

        return em.createQuery(query, Member.class)
                .setParameter("channelId", channelId)
                .setFirstResult(firstPage)
                .setMaxResults(lastPage)
                .getResultList();
    }

    @Override
    public List<Member> findWaitingMemberByChannelId(Long channelId) {
        String query = "select cm.waitingMember from ChannelMember cm join cm.channel c " +
                "where c.id = :channelId";

        return em.createQuery(query, Member.class)
                .setParameter("channelId", channelId)
                .getResultList();
    }

    @Override
    public List<Member> findWaitingMemberByChannelId(Long channelId, int page, int count) {
        int firstPage = (page - 1) * count; //0, 5, 10, 15
        int lastPage = page * count; //5, 10, 15, 20

        String query = "select cm.waitingMember from ChannelMember cm join cm.channel c " +
                "where c.id = :channelId";

        return em.createQuery(query, Member.class)
                .setParameter("channelId", channelId)
                .setFirstResult(firstPage)
                .setMaxResults(lastPage)
                .getResultList();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    /**
     * 삭제
     * */
    @Override
    public void delete(Long id) {
        Member findMember = findById(id);
        em.remove(findMember);
    }

}

package com.levelup.core.repository.member;

import com.levelup.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final EntityManager em;

    @Override
    public Optional<Member> findByEmail(String email) {
        Member member = em.createQuery("select m from Member m " +
                        "join fetch m.emailAuth e " +
                        "where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(member);
    }
}

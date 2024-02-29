package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final EntityManager em;

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        MemberEntity member = em.createQuery("select m from MemberEntity m " +
                        "join fetch m.roles rs " +
                        "where m.email = :email", MemberEntity.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(member);
    }
}

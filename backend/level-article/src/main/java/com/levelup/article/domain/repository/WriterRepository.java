package com.levelup.article.domain.repository;

import com.levelup.article.domain.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {

    Optional<Writer> findByMemberId(Long memberId);
}

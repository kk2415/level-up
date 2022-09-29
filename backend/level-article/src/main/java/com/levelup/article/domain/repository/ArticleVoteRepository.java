package com.levelup.article.domain.repository;

import com.levelup.article.domain.entity.ArticleVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArticleVoteRepository extends JpaRepository<ArticleVote, Long> {

    @Query("select av from ArticleVote av where av.memberId =:memberId and av.article.id =:articleId")
    List<ArticleVote> findByMemberIdAndArticleId(@Param("memberId") Long memberId, @Param("articleId") Long articleId);
}

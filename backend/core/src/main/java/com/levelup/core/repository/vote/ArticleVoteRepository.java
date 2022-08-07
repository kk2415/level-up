package com.levelup.core.repository.vote;


import com.levelup.core.domain.vote.ArticleVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArticleVoteRepository extends JpaRepository<ArticleVote, Long> {

    @Query("select av from ArticleVote av where av.memberId =:memberId and av.article.articleId =:articleId")
    List<ArticleVote> findByMemberIdAndArticleId(@Param("memberId") Long memberId, @Param("articleId") Long articleId);
}

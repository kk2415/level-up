package com.levelup.core.repository.article;

import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleQueryRepository {

    Optional<List<Article>> findByMemberId(Long memberId);

    @EntityGraph(attributePaths = "member")
    Page<Article> findByArticleType(ArticleType articleType, Pageable pageable);

    @EntityGraph(attributePaths = "member")
    @Query("select a from Article a where a.articleType = :articleType and a.title like %:title%")
    Page<Article> findByArticleTypeAndTitle(@Param("articleType") ArticleType articleType,
                                            @Param("title") String title,
                                            Pageable pageable);

    @EntityGraph(attributePaths = "member")
    @Query("select a from Article a where a.articleType = :articleType and a.member.nickname like %:nickname%")
    Page<Article> findByArticleTypeAndNickname(@Param("articleType") ArticleType articleType,
                                               @Param("nickname") String nickname,
                                               Pageable pageable);

    @EntityGraph(attributePaths = "member")
    @Query("select cp from ChannelPost cp where cp.id = :articleId")
    Optional<ChannelPost> findChannelPostById(@Param("articleId") Long articleId);

    @EntityGraph(attributePaths = "member")
    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId")
    Page<ChannelPost> findChannelPostByChannelId(@Param("channelId") Long channelId, Pageable pageable);

    @EntityGraph(attributePaths = "member")
    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.title like %:title%")
    Page<ChannelPost> findByChannelIdAndTitle(@Param("channelId") Long channelId,
                                              @Param("title") String title,
                                              Pageable pageable);

    @EntityGraph(attributePaths = "member")
    @Query("select cp from ChannelPost cp where cp.channel.id = :channelId and cp.member.nickname like %:nickname%")
    Page<ChannelPost> findByChannelIdAndNickname(@Param("channelId") Long channelId,
                                                 @Param("nickname") String nickname,
                                                 Pageable pageable);
}

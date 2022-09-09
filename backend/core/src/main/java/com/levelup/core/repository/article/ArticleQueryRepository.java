package com.levelup.core.repository.article;

import com.levelup.core.domain.article.Article;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;

import java.util.Optional;

public interface ArticleQueryRepository {

//    List<ArticlePagingDto> findByArticleType(ArticleType articleType, Pageable pageable);
    Optional<Article> findNextPageByArticleType(Long articleId, ArticleType articleType);
    Optional<Article> findPrevPageArticleType(Long articleId, ArticleType articleType);
    Optional<ChannelPost> findNextPageByChannelId(Long articleId, Long channelId);
    Optional<ChannelPost> findPrevPageChannelId(Long articleId, Long channelId);
}

package com.levelup.core.repository.article;



import com.levelup.core.domain.Article.Article;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.Article.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface ArticleQueryRepository {

    public Optional<Article> findNextPageByArticleType(Long articleId, ArticleType articleType);
    public Optional<Article> findPrevPageArticleType(Long articleId, ArticleType articleType);
    public Optional<ChannelPost> findNextPageByChannelId(Long articleId, Long channelId);
    public Optional<ChannelPost> findPrevPageChannelId(Long articleId, Long channelId);
    public List<ChannelPost> findByChannelId(Long channelId, SearchCondition postSearch);
    public List<ChannelPost> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch);

}

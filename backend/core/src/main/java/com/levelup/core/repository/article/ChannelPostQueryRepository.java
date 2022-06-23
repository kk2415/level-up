package com.levelup.core.repository.article;



import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.dto.post.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface ChannelPostQueryRepository {

    Optional<ChannelPost> findNextByChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType);
    Optional<ChannelPost> findPrevChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType);

}

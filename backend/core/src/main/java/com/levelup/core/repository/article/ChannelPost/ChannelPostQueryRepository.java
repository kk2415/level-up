package com.levelup.core.repository.article.ChannelPost;

import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import java.util.Optional;

public interface ChannelPostQueryRepository {

    Optional<ChannelPost> findNextByChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType);
    Optional<ChannelPost> findPrevChannelIdAndArticleType(Long articleId, Long channelId, ArticleType articleType);

}

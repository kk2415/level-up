package com.levelup.core.repository.article;



import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.post.Post;
import com.levelup.core.dto.post.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface ArticleQueryRepository {

    public Optional<ChannelPost> findNextPageByChannelId(Long articleId, Long channelId);
    public Optional<ChannelPost> findPrevPageChannelId(Long articleId, Long channelId);
    public List<ChannelPost> findByChannelId(Long channelId, SearchCondition postSearch);
    public List<ChannelPost> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch);

}

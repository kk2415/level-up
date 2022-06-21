package com.levelup.core.repository.article;



import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.post.Post;
import com.levelup.core.dto.post.SearchCondition;

import java.util.List;

public interface ChannelPostQueryRepository {

    public Post findNextPage(Long postId, Long channelId);
    public Post findPrevPage(Long postId, Long channelId);
    public List<ChannelPost> findByChannelId(Long channelId, SearchCondition postSearch);
    public List<ChannelPost> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch);

}

package com.levelup.core.repository.post;



import com.levelup.core.domain.post.Post;
import com.levelup.core.dto.post.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface PostQueryRepository {

    public Post findNextPage(Long postId, Long channelId);
    public Post findPrevPage(Long postId, Long channelId);
    public List<Post> findByChannelId(Long channelId, SearchCondition postSearch);
    public List<Post> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch);

}

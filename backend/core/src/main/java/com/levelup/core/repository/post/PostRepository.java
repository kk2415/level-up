package com.levelup.core.repository.post;



import com.levelup.core.domain.post.Post;
import com.levelup.core.dto.post.SearchCondition;

import java.util.List;

public interface PostRepository {

    public void save(Post post);
    public Post findById(Long id);
    public Post findNextPage(Long id);
    public Post findPrevPage(Long id);
    public List<Post> findByChannelId(Long channelId, SearchCondition postSearch);
    public List<Post> findByChannelId(Long channelId, int page, int postCount, SearchCondition postSearch);
    public List<Post> findByMemberId(Long memberId);
    public List<Post> findByTitle(String title);
    public List<Post> findByWriter(String writer);
    public List<Post> findAll();
    public void delete(Long id);

}

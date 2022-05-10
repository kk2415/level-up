package com.together.levelup.repository.post;

import com.together.levelup.domain.post.Post;
import com.together.levelup.dto.post.PostSearch;

import java.util.List;

public interface PostRepository {

    public void save(Post post);
    public Post findById(Long id);
    public Post findNextPage(Long id);
    public Post findPrevPage(Long id);
    public List<Post> findByChannelId(Long channelId, PostSearch postSearch);
    public List<Post> findByChannelId(Long channelId, int page, int postCount, PostSearch postSearch);
    public List<Post> findByMemberId(Long memberId);
    public List<Post> findByTitle(String title);
    public List<Post> findByWriter(String writer);
    public List<Post> findAll();
    public void delete(Long id);

}

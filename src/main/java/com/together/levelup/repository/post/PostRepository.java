package com.together.levelup.repository.post;

import com.together.levelup.domain.Post;

import java.util.List;

public interface PostRepository {

    public void save(Post post);
    public Post findById(Long id);
    public List<Post> findByMemberId(Long memberId);
    public List<Post> findByTitle(String title);
    public List<Post> findByWriter(String writer);
    public List<Post> findAll();
    public void delete(Long id);
    public Long countAll();

}

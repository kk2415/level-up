package com.together.community.repository.post;

import com.together.community.domain.Post;

import java.util.List;

public interface PostRepository {

    public void save(Post post);
    public Post findById(Long id);
    public List<Post> findByTitle(String title);
    public List<Post> findByWriter(String writer);
    public List<Post> findAll();

}

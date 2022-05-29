package com.levelup.core.dto.post;

import com.levelup.core.domain.post.PostCategory;
import lombok.Data;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long views;
    private int commentCount;
    private PostCategory category;

    public PostResponse(String title, String writer, String content, PostCategory category, String dateCreated, Long voteCount, Long views, int commentCount) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.category = category;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
    }

    public PostResponse(Long id, String title, String writer, String content, PostCategory category, String dateCreated, Long voteCount, Long views, int commentCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.category = category;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
    }
}

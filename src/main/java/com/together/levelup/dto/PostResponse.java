package com.together.levelup.dto;

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

    public PostResponse(String title, String writer, String content, String dateCreated, Long voteCount, Long views, int commentCount) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
    }

    public PostResponse(Long id, String title, String writer, String content, String dateCreated, Long voteCount, Long views, int commentCount) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
    }
}

package com.levelup.core.dto.post;

import com.levelup.core.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collector;

@Data
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long views;
    private int commentCount;
    private PostCategory category;
}

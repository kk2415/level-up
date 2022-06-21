package com.levelup.core.dto.post;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.post.Post;
import com.levelup.core.domain.post.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    public PostResponse(Post post) {
        this.id = post.getId();
        this.memberId = post.getMember().getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.content = post.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(post.getCreateAt());
        this.voteCount = post.getVoteCount();
        this.views = post.getViews();
        this.commentCount = post.getCommentCount();
        this.category = post.getPostCategory();
    }

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private PostCategory category;

}

package com.levelup.core.dto.article;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.Article.ChannelPost;
import com.levelup.core.domain.Article.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelPostResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private PostCategory postCategory;
    private ArticleType articleType;

    public ChannelPostResponse(ChannelPost channelPost) {
        this.id = channelPost.getArticleId();
        this.memberId = channelPost.getMember().getId();
        this.title = channelPost.getTitle();
        this.writer = channelPost.getWriter();
        this.content = channelPost.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channelPost.getCreateAt());
        this.voteCount = channelPost.getVoteCount();
        this.views = channelPost.getViews();
        this.commentCount = channelPost.getCommentCount();
        this.postCategory = channelPost.getPostCategory();
        this.articleType = channelPost.getArticleType();
    }
}

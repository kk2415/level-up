package com.levelup.api.dto.channelPost;

import com.levelup.core.DateFormat;
import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.PostCategory;
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

    private ChannelPostResponse(ChannelPost channelPost) {
        this.id = channelPost.getId();
        this.memberId = channelPost.getMember().getId();
        this.title = channelPost.getTitle();
        this.writer = channelPost.getMember().getNickname();
        this.content = channelPost.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT).format(channelPost.getCreatedAt());
        this.voteCount = (long) channelPost.getArticleVotes().size();
        this.views = channelPost.getViews();
        this.commentCount = (long) channelPost.getComments().size();
        this.postCategory = channelPost.getChannelPostCategory();
        this.articleType = channelPost.getArticleType();
    }

    public static ChannelPostResponse from(ChannelPost channelPost) {
        return new ChannelPostResponse(channelPost);
    }
}

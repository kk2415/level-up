package com.levelup.api.dto.response.channelPost;

import com.levelup.api.dto.service.channelPost.ChannelPostDto;
import com.levelup.core.DateFormat;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channelPost.PostCategory;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
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

    protected ChannelPostResponse() {}

    public ChannelPostResponse(Long id,
                               Long memberId,
                               String title,
                               String writer,
                               String content,
                               String dateCreated,
                               Long voteCount,
                               Long views,
                               Long commentCount,
                               PostCategory postCategory,
                               ArticleType articleType)
    {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
        this.postCategory = postCategory;
        this.articleType = articleType;
    }

    public static ChannelPostResponse from(ChannelPostDto dto) {
        return new ChannelPostResponse(
                dto.getChannelPostId(),
                dto.getMemberId(),
                dto.getTitle(),
                dto.getWriter(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getViews(),
                dto.getCommentCount(),
                dto.getPostCategory(),
                dto.getArticleType()
        );
    }

    private ChannelPostResponse(ChannelPost channelPost) {
        this.id = channelPost.getId();
        this.memberId = channelPost.getMember().getId();
        this.title = channelPost.getTitle();
        this.writer = channelPost.getMember().getNickname();
        this.content = channelPost.getContent();
        this.dateCreated = DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(channelPost.getCreatedAt());
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

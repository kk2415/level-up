package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.common.util.DateFormat;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelArticleCategory;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ChannelArticleResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private String createdAt;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ChannelArticleCategory category;

    protected ChannelArticleResponse() {}

    public ChannelArticleResponse(Long id,
                                  Long memberId,
                                  String title,
                                  String writer,
                                  String content,
                                  String createdAt,
                                  Long voteCount,
                                  Long views,
                                  Long commentCount,
                                  ChannelArticleCategory category)
    {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.views = views;
        this.commentCount = commentCount;
        this.category = category;
    }

    public static ChannelArticleResponse from(ChannelArticleDto dto) {
        return new ChannelArticleResponse(
                dto.getChannelPostId(),
                dto.getMemberId(),
                dto.getTitle(),
                dto.getWriter(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getViews(),
                dto.getCommentCount(),
                dto.getPostCategory()
        );
    }

    private ChannelArticleResponse(ChannelArticle channelPost) {
        this.id = channelPost.getId();
        this.memberId = channelPost.getChannelMember().getMember().getId();
        this.title = channelPost.getTitle();
        this.writer = channelPost.getChannelMember().getMember().getNickname();
        this.content = channelPost.getContent();
        this.createdAt = DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(channelPost.getCreatedAt());
        this.voteCount = (long) channelPost.getVotes().size();
        this.views = channelPost.getViews();
        this.commentCount = (long) channelPost.getComments().size();
        this.category = channelPost.getCategory();
    }

    public static ChannelArticleResponse from(ChannelArticle channelPost) {
        return new ChannelArticleResponse(channelPost);
    }
}

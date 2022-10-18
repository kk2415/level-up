package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.Channel;
import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelArticleCategory;
import com.levelup.channel.domain.entity.ChannelMember;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
@Getter
public class ChannelArticleDto implements Serializable {

    private Long channelPostId;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private Long voteCount;
    private Long views;
    private Long commentCount;
    private ChannelArticleCategory category;

    protected ChannelArticleDto() {}

    public ChannelArticleDto(
            Long channelPostId,
            Long memberId,
            String title,
            String writer,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long views,
            Long commentCount,
            ChannelArticleCategory category)
    {
        this.channelPostId = channelPostId;
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

    public static ChannelArticleDto from(ChannelArticle channelArticle) {
        return new ChannelArticleDto(
            channelArticle.getId(),
            channelArticle.getChannelMember().getId(),
            channelArticle.getTitle(),
            channelArticle.getChannelMember().getNickname(),
            channelArticle.getContent(),
            channelArticle.getCreatedAt(),
            (long) channelArticle.getVotes().size(),
            channelArticle.getViews(),
            (long) channelArticle.getComments().size(),
            channelArticle.getCategory()
        );
    }

    public ChannelArticle toEntity(ChannelMember channelMember, Channel channel) {
        return ChannelArticle.of(null, category, content, title, channel, channelMember);
    }
}

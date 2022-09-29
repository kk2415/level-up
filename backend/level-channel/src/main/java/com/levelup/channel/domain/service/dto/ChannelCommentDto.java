package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelMember;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class ChannelCommentDto {

    private Long commentId;
    private Long memberId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private Long voteCount;
    private Long replyCount;

    protected ChannelCommentDto() {}

    private ChannelCommentDto(
            Long commentId,
            Long memberId,
            String nickname,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long replyCount)
    {
        this.commentId = commentId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
    }

    public static ChannelCommentDto of(
            Long commentId,
            Long memberId,
            String nickname,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long replyCount
    ) {
        return new ChannelCommentDto(
                commentId,
                memberId,
                nickname,
                content,
                createdAt,
                voteCount,
                replyCount);
    }

    public static ChannelCommentDto from(String content) {
        return new ChannelCommentDto(
                null,
                null,
                null,
                content,
                null,
                null,
                null
        );
    }

    public static ChannelCommentDto from(ChannelComment comment) {
        return new ChannelCommentDto(
                comment.getId(),
                comment.getChannelMember().getMember().getId(),
                comment.getChannelMember().getMember().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                (long) comment.getVotes().size(),
                (long) comment.getReplies().size()
        );
    }

    public ChannelComment toEntity(ChannelMember channelMember, ChannelArticle article) {
        ChannelComment comment = ChannelComment.builder()
                .content(content)
                .replies(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        comment.setChannelMember(channelMember);
        comment.setArticle(article);
        return comment;
    }
}

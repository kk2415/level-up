package com.levelup.channel.domain.service.dto;

import com.levelup.channel.domain.entity.ChannelArticle;
import com.levelup.channel.domain.entity.ChannelComment;
import com.levelup.channel.domain.entity.ChannelMember;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class ChannelReplyCommentDto {

    private Long commentId;
    private Long memberId;
    private Long channelId;
    private Long parentId;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private Long voteCount;
    private Long replyCount;
    private Long articleId;

    private ChannelReplyCommentDto(
            Long commentId,
            Long memberId,
            Long channelId,
            Long parentId,
            String writer,
            String content,
            LocalDateTime createdAt,
            Long voteCount,
            Long replyCount,
            Long articleId)
    {
        this.commentId = commentId;
        this.memberId = memberId;
        this.channelId = channelId;
        this.parentId = parentId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
        this.articleId = articleId;
    }

    public static ChannelReplyCommentDto from(ChannelComment comment) {
        return new ChannelReplyCommentDto(
            comment.getId(),
            comment.getChannelMember().getMemberId(),
            comment.getChannelMember().getChannel().getId(),
            comment.getParent().getId(),
            comment.getChannelMember().getNickname(),
            comment.getContent(),
            comment.getCreatedAt(),
            (long) comment.getVotes().size(),
            (long) comment.getReplies().size(),
            comment.getArticle().getId()
        );
    }

    public static ChannelReplyCommentDto of(Long memberId, Long articleId, Long channelId, Long parentId, String content) {
        return new ChannelReplyCommentDto(
                null,
                memberId,
                channelId,
                parentId,
                null,
                content,
                null,
                null,
                null,
                articleId);
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

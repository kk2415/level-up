package com.levelup.api.controller.v1.dto.response.channel;

import com.levelup.channel.domain.service.dto.ChannelCommentDto;
import com.levelup.common.util.DateFormat;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChannelCommentResponse {

    private Long commentId;
    private Long memberId;
    private String nickname;
    private String content;
    private String createdAt;
    private Long voteCount;
    private Long replyCount;

    protected ChannelCommentResponse() {}

    public ChannelCommentResponse(
            Long commentId,
            Long memberId,
            String nickname,
            String content,
            String createdAt,
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

    public static ChannelCommentResponse from(ChannelCommentDto dto) {
        return new ChannelCommentResponse(
                dto.getCommentId(),
                dto.getMemberId(),
                dto.getNickname(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getReplyCount()
        );
    }
}

package com.levelup.api.controller.v1.dto.response.comment;

import com.levelup.api.util.DateFormat;
import com.levelup.api.service.dto.comment.CommentDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
public class CommentResponse {

    private Long commentId;
    private Long memberId;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long replyCount;

    protected CommentResponse() {}

    private CommentResponse(Long commentId,
                            Long memberId,
                            String writer,
                            String content,
                            String dateCreated,
                            Long voteCount,
                            Long replyCount) {
        this.commentId = commentId;
        this.memberId = memberId;
        this.writer = writer;
        this.content = content;
        this.dateCreated = dateCreated;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
    }

    public static CommentResponse from(CommentDto dto) {
        return new CommentResponse(
                dto.getCommentId(),
                dto.getMemberId(),
                dto.getWriter(),
                dto.getContent(),
                DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_FORMAT).format(dto.getCreatedAt()),
                dto.getVoteCount(),
                dto.getReplyCount()
        );
    }
}
